package com.ghh.framework.web.security.restauth.server.basic;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/*****************************************************************
 *
 * 自定义基本认证鉴权过滤器，增加单点登录接口对用户名和验证的用户名进行匹配校验
 *
 * @author ghh
 * @date 2018年12月19日下午10:57:33
 * @since v1.0.1
 ****************************************************************/
public class GhhBasicAuthenticationFilter extends OncePerRequestFilter {

	// ~ Instance fields
	// ================================================================================================

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private AuthenticationEntryPoint authenticationEntryPoint;
	private AuthenticationManager authenticationManager;
	private RememberMeServices rememberMeServices = new NullRememberMeServices();
	private boolean ignoreFailure = false;
	private String credentialsCharset = "UTF-8";

	/**
	 * Creates an instance which will authenticate against the supplied
	 * {@code AuthenticationManager} and which will ignore failed authentication
	 * attempts, allowing the request to proceed down the filter chain.
	 *
	 * @param authenticationManager
	 *            the bean to submit authentication requests to
	 */
	public GhhBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		Assert.notNull(authenticationManager, "authenticationManager cannot be null");
		this.authenticationManager = authenticationManager;
		ignoreFailure = true;
	}

	/**
	 * Creates an instance which will authenticate against the supplied
	 * {@code AuthenticationManager} and use the supplied
	 * {@code AuthenticationEntryPoint} to handle authentication failures.
	 *
	 * @param authenticationManager
	 *            the bean to submit authentication requests to
	 * @param authenticationEntryPoint
	 *            will be invoked when authentication fails. Typically an
	 *            instance of {@link BasicAuthenticationEntryPoint}.
	 */
	public GhhBasicAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		Assert.notNull(authenticationManager, "authenticationManager cannot be null");
		Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
		this.authenticationManager = authenticationManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	// ~ Methods
	// ========================================================================================================

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");

		if (!isIgnoreFailure()) {
			Assert.notNull(this.authenticationEntryPoint, "An AuthenticationEntryPoint is required");
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final boolean debug = logger.isDebugEnabled();

		String header = request.getHeader("Authorization");

		if ((header == null) || !header.startsWith("Basic ")) {
			chain.doFilter(request, response);
			return;
		}

		try {
			String[] tokens = extractAndDecodeHeader(header, request);
			assert tokens.length == 2;

			String username = tokens[0];

			// 对外提供的单点登录接口，对用户名和验证的用户名进行匹配校验 2016-11-02
			if (request.getPathInfo().indexOf("/sso/token/") != -1) {
				if (!("/sso/token/" + username).equals(request.getPathInfo())) {
					logger.error("Uri请求路径{" + request.getPathInfo() + "}的登录名和验证的登录不匹配！");
					throw new BadCredentialsException(
							"BasicAuthenticationFilter.usernameNotMatch , Username {" + username + "} not match");
				}
			}

			if (debug) {
				logger.debug("Basic Authentication Authorization header found for user '" + username + "'");
			}

			if (authenticationIsRequired(username)) {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
						tokens[1]);
				authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
				Authentication authResult = authenticationManager.authenticate(authRequest);

				if (debug) {
					logger.debug("Authentication success: " + authResult);
				}

				SecurityContextHolder.getContext().setAuthentication(authResult);

				rememberMeServices.loginSuccess(request, response, authResult);

				onSuccessfulAuthentication(request, response, authResult);
			}

		} catch (AuthenticationException failed) {
			SecurityContextHolder.clearContext();

			if (debug) {
				logger.debug("Authentication request for failed: " + failed);
			}

			rememberMeServices.loginFail(request, response);

			onUnsuccessfulAuthentication(request, response, failed);

			if (ignoreFailure) {
				chain.doFilter(request, response);
			} else {
				authenticationEntryPoint.commence(request, response, failed);
			}

			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * Decodes the header into a username and password.
	 *
	 * @throws BadCredentialsException
	 *             if the Basic header is not present or is not valid Base64
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, getCredentialsCharset(request));

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

	private boolean authenticationIsRequired(String username) {
		// Only reauthenticate if username doesn't match SecurityContextHolder
		// and user
		// isn't authenticated
		// (see SEC-53)
		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

		if ((existingAuth == null) || !existingAuth.isAuthenticated()) {
			return true;
		}

		// Limit username comparison to providers which use usernames (ie
		// UsernamePasswordAuthenticationToken)
		// (see SEC-348)

		if ((existingAuth instanceof UsernamePasswordAuthenticationToken) && !existingAuth.getName().equals(username)) {
			return true;
		}

		// Handle unusual condition where an AnonymousAuthenticationToken is
		// already
		// present
		// This shouldn't happen very often, as BasicProcessingFitler is meant
		// to be
		// earlier in the filter
		// chain than AnonymousAuthenticationFilter. Nevertheless, presence of
		// both an
		// AnonymousAuthenticationToken
		// together with a BASIC authentication request header should indicate
		// reauthentication using the
		// BASIC protocol is desirable. This behaviour is also consistent with
		// that
		// provided by form and digest,
		// both of which force re-authentication if the respective header is
		// detected (and
		// in doing so replace
		// any existing AnonymousAuthenticationToken). See SEC-610.
		if (existingAuth instanceof AnonymousAuthenticationToken) {
			return true;
		}

		return false;
	}

	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
	}

	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException {
	}

	protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	protected AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	protected boolean isIgnoreFailure() {
		return ignoreFailure;
	}

	public void setAuthenticationDetailsSource(
			AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	public void setRememberMeServices(RememberMeServices rememberMeServices) {
		Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
		this.rememberMeServices = rememberMeServices;
	}

	public void setCredentialsCharset(String credentialsCharset) {
		Assert.hasText(credentialsCharset, "credentialsCharset cannot be null or empty");
		this.credentialsCharset = credentialsCharset;
	}

	protected String getCredentialsCharset(HttpServletRequest httpRequest) {
		return credentialsCharset;
	}
}
