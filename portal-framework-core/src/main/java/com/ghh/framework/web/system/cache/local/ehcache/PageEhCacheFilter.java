package com.ghh.framework.web.system.cache.local.ehcache;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.ghh.framework.GhhException;
import com.ghh.framework.core.persistence.PageCacheManager;

/*****************************************************************
 *
 * 页面缓存过滤
 *
 * @author ghh
 * @date 2018年12月20日下午6:59:06
 * @since v1.0.1
 ****************************************************************/
public class PageEhCacheFilter extends SimplePageCachingFilter {
	private final static Logger log = Logger.getLogger(PageEhCacheFilter.class);
	private final static String FILTER_URL_PATTERNS = "patterns";
	private static String[] cacheURLs;
	private PageCacheManager cache = null;

	private void init() throws CacheException {
		String patterns = filterConfig.getInitParameter(FILTER_URL_PATTERNS);
		cacheURLs = StringUtils.split(patterns, ",");
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		cache = (PageCacheManager) ctx.getBean("pageCacheManager");
		try {
			cache.initPageCache();
			log.info("页面缓存初始化 成功！");
		} catch (GhhException e) {
			log.error("页面缓存初始化错误：" + e.getMessage());
		}
	}

	@Override
	protected void doFilter(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain) throws AlreadyGzippedException, AlreadyCommittedException,
			FilterNonReentrantException, LockTimeoutException, Exception {
		if (cacheURLs == null) {
			init();
		}
		String uri = request.getRequestURI();
		boolean flag = false;
		// 配置在web.xml上需要缓存的页面
		if (cacheURLs != null && cacheURLs.length > 0) {
			for (String cacheURL : cacheURLs) {
				if (uri.contains(cacheURL.trim())) {
					flag = true;
					break;
				}
			}
		}
		// 配置在权限列表上需要缓存的页面
		if (flag == false
				&& cache.isCached(uri.replace(request.getContextPath() + "/", "") + "?" + request.getQueryString())) {
			flag = true;
		}
		// 如果包含我们要缓存的url 就缓存该页面，否则执行正常的页面转向
		if (flag) {
			String query = request.getQueryString();
			if (query != null) {
				query = "?" + query;
			}
			log.info("当前请求被缓存：" + uri + query);
			super.doFilter(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

}
