package com.ghh.framework.web.security.restauth.server.digest;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.ghh.framework.common.entities.Sysdigestauth;
import com.ghh.framework.core.dao.HBUtil;

/*****************************************************************
 *
 * 改造DigestAuthenticationFilter的doFilter方法
 *
 * @author ghh
 * @date 2018年12月19日下午10:58:52
 * @since v1.0.1
 ****************************************************************/
public class GhhDigestAuthenticationFilter extends GenericFilterBean implements MessageSourceAware {
	private static final Log logger = LogFactory.getLog(GhhDigestAuthenticationFilter.class);

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private DigestAuthenticationEntryPoint authenticationEntryPoint;
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private UserCache userCache = new NullUserCache();
	private UserDetailsService userDetailsService;
	private boolean passwordAlreadyEncoded = false;
	private boolean createAuthenticatedToken = false;

	// ~ Methods
	// ========================================================================================================

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(userDetailsService, "A UserDetailsService is required");
		Assert.notNull(authenticationEntryPoint, "A DigestAuthenticationEntryPoint is required");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String header = request.getHeader("Authorization");

		if ((header == null) || !header.startsWith("Digest ")) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Digest Authorization header received from user agent: " + header);
		}

		DigestData digestAuth = new DigestData(header);

		try {
			digestAuth.validateAndDecode(authenticationEntryPoint.getKey(), authenticationEntryPoint.getRealmName());
		} catch (BadCredentialsException e) {
			fail(request, response, e);

			return;
		}

		// 对外提供的单点登录接口，对用户名和验证的用户名进行匹配校验 2016-11-02
		if (request.getPathInfo().indexOf("/sso/token/") != -1) {
			if (!("/sso/token/" + digestAuth.getUsername()).equals(request.getPathInfo())) {
				logger.error("Uri请求路径{" + request.getPathInfo() + "}的登录名和验证的登录不匹配！");
				fail(request, response,
						new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.usernameNotMatch",
								new Object[] { digestAuth.getUsername() }, "Username {0} not match")));

				return;
			}
		}

		// Lookup password for presented username
		// NB: DAO-provided password MUST be clear text - not encoded/salted
		// (unless this instance's passwordAlreadyEncoded property is 'false')
		boolean cacheWasUsed = true;
		UserDetails user = userCache.getUserFromCache(digestAuth.getUsername());
		String serverDigestMd5;
		String password = null;
		Sysdigestauth auth = null;
		try {
			if (user == null) {
				cacheWasUsed = false;
				user = userDetailsService.loadUserByUsername(digestAuth.getUsername());

				if (user == null) {
					throw new AuthenticationServiceException(
							"AuthenticationDao returned null, which is an interface contract violation");
				}
				// 获取存在本地系统可以逆转的密码
				Session session = HBUtil.openNewSession();

				try {
					Query query = session.createQuery("from Sysdigestauth a where a.loginname =:loginname");
					query.setString("loginname", user.getUsername());
					auth = (Sysdigestauth) query.uniqueResult();
					if (auth != null) {
						password = auth.getDigestpasswd();
					} else {
						logger.info("无法获取摘要密码出错!");
					}
				} catch (Exception e) {
					logger.info("获取摘要密码出错：" + e.getMessage());
				} finally {
					session.close(); // 释放资源
				}
				userCache.putUserInCache(user);
			}
			if (password != null) {
				String basePasswd = new String(Base64.decode(password.getBytes("UTF-8")));
				if (basePasswd.indexOf(":") != -1) {
					String[] str = basePasswd.split(":");
					if (auth.getPasswd().equals(str[1])) {
						password = str[0];
					} else {
						logger.info("摘要密码核对出错，后台系统存放密码不一致！");
					}
				}
			}
			serverDigestMd5 = digestAuth.calculateServerDigest(password, request.getMethod());

			// If digest is incorrect, try refreshing from backend and
			// recomputing
			if (!serverDigestMd5.equals(digestAuth.getResponse()) && cacheWasUsed) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"Digest comparison failure; trying to refresh user from DAO in case password had changed");
				}

				user = userDetailsService.loadUserByUsername(digestAuth.getUsername());
				userCache.putUserInCache(user);
				// user.getPassword()
				serverDigestMd5 = digestAuth.calculateServerDigest(password, request.getMethod());
			}

		} catch (UsernameNotFoundException notFound) {
			fail(request, response,
					new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.usernameNotFound",
							new Object[] { digestAuth.getUsername() }, "Username {0} not found")));

			return;
		}

		// If digest is still incorrect, definitely reject authentication
		// attempt
		if (!serverDigestMd5.equals(digestAuth.getResponse())) {
			if (logger.isDebugEnabled()) {
				logger.debug("Expected response: '" + serverDigestMd5 + "' but received: '" + digestAuth.getResponse()
						+ "'; is AuthenticationDao returning clear text passwords?");
			}

			fail(request, response, new BadCredentialsException(
					messages.getMessage("DigestAuthenticationFilter.incorrectResponse", "Incorrect response")));
			return;
		}

		// To get this far, the digest must have been valid
		// Check the nonce has not expired
		// We do this last so we can direct the user agent its nonce is stale
		// but the request was otherwise appearing to be valid
		if (digestAuth.isNonceExpired()) {
			fail(request, response, new NonceExpiredException(
					messages.getMessage("DigestAuthenticationFilter.nonceExpired", "Nonce has expired/timed out")));

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success for user: '" + digestAuth.getUsername() + "' with response: '"
					+ digestAuth.getResponse() + "'");
		}

		Authentication authentication = createSuccessfulAuthentication(request, user);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);

		chain.doFilter(request, response);
	}

	private Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
		UsernamePasswordAuthenticationToken authRequest;
		if (createAuthenticatedToken) {
			authRequest = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		} else {
			authRequest = new UsernamePasswordAuthenticationToken(user, user.getPassword());
		}

		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

		return authRequest;
	}

	private void fail(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(null);

		if (logger.isDebugEnabled()) {
			logger.debug(failed);
		}

		authenticationEntryPoint.commence(request, response, failed);
	}

	protected final DigestAuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	public UserCache getUserCache() {
		return userCache;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setAuthenticationEntryPoint(DigestAuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public void setPasswordAlreadyEncoded(boolean passwordAlreadyEncoded) {
		this.passwordAlreadyEncoded = passwordAlreadyEncoded;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	/**
	 * If you set this property, the Authentication object, which is created
	 * after the successful digest authentication will be marked as
	 * <b>authenticated</b> and filled with the authorities loaded by the
	 * UserDetailsService. It therefore will not be re-authenticated by your
	 * AuthenticationProvider. This means, that only the password of the user is
	 * checked, but not the flags like isEnabled() or isAccountNonExpired(). You
	 * will save some time by enabling this flag, as otherwise your
	 * UserDetailsService will be called twice. A more secure option would be to
	 * introduce a cache around your UserDetailsService, but if you don't use
	 * these flags, you can also safely enable this option.
	 *
	 * @param createAuthenticatedToken
	 *            default is false
	 */
	public void setCreateAuthenticatedToken(boolean createAuthenticatedToken) {
		this.createAuthenticatedToken = createAuthenticatedToken;
	}

	private class DigestData {
		private final String username;
		private final String realm;
		private final String nonce;
		private final String uri;
		private final String response;
		private final String qop;
		private final String nc;
		private final String cnonce;
		private final String section212response;
		private long nonceExpiryTime;

		DigestData(String header) {
			section212response = header.substring(7);
			String[] headerEntries = DigestAuthUtils.splitIgnoringQuotes(section212response, ',');
			Map<String, String> headerMap = DigestAuthUtils.splitEachArrayElementAndCreateMap(headerEntries, "=", "\"");

			username = headerMap.get("username");
			realm = headerMap.get("realm");
			nonce = headerMap.get("nonce");
			uri = headerMap.get("uri");
			response = headerMap.get("response");
			qop = headerMap.get("qop"); // RFC 2617 extension
			nc = headerMap.get("nc"); // RFC 2617 extension
			cnonce = headerMap.get("cnonce"); // RFC 2617 extension

			if (logger.isDebugEnabled()) {
				logger.debug("Extracted username: '" + username + "'; realm: '" + realm + "'; nonce: '" + nonce
						+ "'; uri: '" + uri + "'; response: '" + response + "'");
			}
		}

		void validateAndDecode(String entryPointKey, String expectedRealm) throws BadCredentialsException {
			// Check all required parameters were supplied (ie RFC 2069)
			if ((username == null) || (realm == null) || (nonce == null) || (uri == null) || (response == null)) {
				throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.missingMandatory",
						new Object[] { section212response }, "Missing mandatory digest value; received header {0}"));
			}
			// Check all required parameters for an "auth" qop were supplied (ie
			// RFC 2617)
			if ("auth".equals(qop)) {
				if ((nc == null) || (cnonce == null)) {
					if (logger.isDebugEnabled()) {
						logger.debug("extracted nc: '" + nc + "'; cnonce: '" + cnonce + "'");
					}

					throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.missingAuth",
							new Object[] { section212response },
							"Missing mandatory digest value; received header {0}"));
				}
			}

			// Check realm name equals what we expected
			if (!expectedRealm.equals(realm)) {
				throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.incorrectRealm",
						new Object[] { realm, expectedRealm },
						"Response realm name '{0}' does not match system realm name of '{1}'"));
			}

			// Check nonce was Base64 encoded (as sent by
			// DigestAuthenticationEntryPoint)
			if (!Base64.isBase64(nonce.getBytes())) {
				throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceEncoding",
						new Object[] { nonce }, "Nonce is not encoded in Base64; received nonce {0}"));
			}

			// Decode nonce from Base64
			// format of nonce is:
			// base64(expirationTime + ":" + md5Hex(expirationTime + ":" + key))
			String nonceAsPlainText = new String(Base64.decode(nonce.getBytes()));
			String[] nonceTokens = StringUtils.delimitedListToStringArray(nonceAsPlainText, ":");

			if (nonceTokens.length != 2) {
				throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceNotTwoTokens",
						new Object[] { nonceAsPlainText }, "Nonce should have yielded two tokens but was {0}"));
			}

			// Extract expiry time from nonce

			try {
				nonceExpiryTime = new Long(nonceTokens[0]).longValue();
			} catch (NumberFormatException nfe) {
				throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceNotNumeric",
						new Object[] { nonceAsPlainText },
						"Nonce token should have yielded a numeric first token, but was {0}"));
			}

			// Check signature of nonce matches this expiry time
			String expectedNonceSignature = DigestAuthUtils.md5Hex(nonceExpiryTime + ":" + entryPointKey);

			if (!expectedNonceSignature.equals(nonceTokens[1])) {
				throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceCompromised",
						new Object[] { nonceAsPlainText }, "Nonce token compromised {0}"));
			}
		}

		String calculateServerDigest(String password, String httpMethod) {
			// Compute the expected response-digest (will be in hex form)

			// Don't catch IllegalArgumentException (already checked validity)
			return DigestAuthUtils.generateDigest(passwordAlreadyEncoded, username, realm, password, httpMethod, uri,
					qop, nonce, nc, cnonce);
		}

		boolean isNonceExpired() {
			long now = System.currentTimeMillis();
			return nonceExpiryTime < now;
		}

		String getUsername() {
			return username;
		}

		String getResponse() {
			return response;
		}
	}
}
