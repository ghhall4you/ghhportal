package com.ghh.framework.web.security.restauth.client.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.ghh.framework.util.GlobalNames;

/*****************************************************************
 *
 * 认证请求工厂类，实现自定义的认证
 *
 * @author ghh
 * @date 2018年12月19日下午10:55:59
 * @since v1.0.1
 ****************************************************************/
public abstract class GhhHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

	public GhhHttpComponentsClientHttpRequestFactory(HttpClient httpClient) {
		super(httpClient);
	}

	@Override
	protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
		// 针对URI中的userInfo做一个特殊处理，解决特殊情况下需要在userInfo中传特殊参数 2016-11-18
		if (uri.getUserInfo() != null) {
			try {
				uri = new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(),
						uri.getFragment());
			} catch (URISyntaxException e) {
				GlobalNames.log.error("重新构造URI失败：" + e.getMessage());
			}
		}
		switch (httpMethod) {
		case GET:
			return new HttpGet(uri);
		case DELETE:
			return new HttpDelete(uri);
		case HEAD:
			return new HttpHead(uri);
		case OPTIONS:
			return new HttpOptions(uri);
		case POST:
			return new HttpPost(uri);
		case PUT:
			return new HttpPut(uri);
		case TRACE:
			return new HttpTrace(uri);
		case PATCH:
			return new HttpPatch(uri);
		default:
			throw new IllegalArgumentException("Invalid HTTP method: " + httpMethod);
		}
	}

	/**
	 * 重写HttpComponentsClientHttpRequestFactory的方法
	 */
	@Override
	protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
		Map<String, String> map = null;
		if (StringUtils.isEmpty(uri.getUserInfo())) {
			map = this.getLoginnameAndPasswd(uri.getHost(), uri.getPort());
		} else {
			map = this.getLoginnameAndPasswd(uri.getHost(), uri.getPort(), uri.getUserInfo().split(":")[0]);
		}

		if (map == null) {
			GlobalNames.log.error("无法找到Restful接口的认证类型：" + uri.getAuthority());
			return null;
		}

		if ("basic".equals(map.get("authType"))) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			Credentials defaultCreds = new UsernamePasswordCredentials(map.get("loginName"), map.get("password"));
			HttpHost host = new HttpHost(uri.getHost(), uri.getPort());
			credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), defaultCreds);
			AuthCache authCache = new BasicAuthCache();
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(host, basicAuth);
			HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(credsProvider);
			context.setAuthCache(authCache);
			return context;
		} else if ("digest".equals(map.get("authType"))) {
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			Credentials defaultCreds = new UsernamePasswordCredentials(map.get("loginName"), map.get("password"));
			HttpHost host = new HttpHost(uri.getHost(), uri.getPort());
			credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), defaultCreds);
			AuthCache authCache = new BasicAuthCache();
			DigestScheme digestAuth = new DigestScheme();
			digestAuth.overrideParamter("realm", "some realm");
			digestAuth.overrideParamter("nonce", "whatever");
			authCache.put(host, digestAuth);
			HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(credsProvider);
			context.setAuthCache(authCache);
			return context;
		} else {
			return super.createHttpContext(httpMethod, uri);
		}
	}

	/**
	 * 根据IP地址、端口号获取配置的参数信息
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	protected Map<String, String> getLoginnameAndPasswd(String ip, int port) {
		return null;
	}

	/**
	 * 根据IP地址、端口号和登录名获取配置的参数信息
	 * 
	 * @param ip
	 * @param port
	 * @param loginName
	 * @return
	 */
	protected Map<String, String> getLoginnameAndPasswd(String ip, int port, String loginName) {
		return null;
	}

}
