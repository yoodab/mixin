package com.cos.mixin.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MyFilter3 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		ServletResponse res = (ServletResponse) response;
		
		if (req.getMethod().equals("POST")) {
			
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);
			
			if(headerAuth.equals("cos")) {
				chain.doFilter(request, response);
			}else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
	}

}
