package com.hackathon.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",  "Cache-Control, "
				+ "X-Authorization, Content-Type, Accept, "
				+ "X-Requested-With, remember-me, Authorization, Access-Control-Request-Headers");
		response.setHeader("Access-Control-Expose-Headers", "Authorization");

		if (notPreflight(request)) {
			chain.doFilter(req, res);
		}
	}
	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}

	private boolean notPreflight(HttpServletRequest request) {
		return !"OPTIONS".equals(request.getMethod());
	}
	
}