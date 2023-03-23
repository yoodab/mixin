package com.cos.mixin.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.mixin.config.auth.PrincipalDetails;
import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private UserRepository userRepository;
	
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
		System.out.println("jwtHeader : " + jwtHeader);
		
		if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
			
		}
		
		
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨");
		
		
		String jwtToken = request.getHeader(JwtProperties.HEADER_STRING)
				.replace(JwtProperties.TOKEN_PREFIX, "");
		
		
		String username = 
				JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken)
				.getClaim("username").asString();
		
		if(username !=null) {
			User userEntity = userRepository.findByUserEmail(username);
			
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
			
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
		
	}
}
