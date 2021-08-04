package org.javaweb.vuls.servlet;

import jakarta.servlet.*;

import java.io.IOException;

public class TestFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
		System.out.println("TestFilter init...");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		new RuntimeException().printStackTrace();
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		System.out.println("TestFilter destroy...");
	}

}
