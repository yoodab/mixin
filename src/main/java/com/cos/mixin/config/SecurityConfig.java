package com.cos.mixin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.cos.mixin.config.jwt.JwtAuthenticationFilter;
import com.cos.mixin.config.jwt.JwtAuthorizationFilter;
import com.cos.mixin.domain.user.UserRepository;


import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	
	private final CorsConfig corsConfig;
	private final UserRepository userRepository;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		log.debug("디버그: BCryptPasswordEncoder 빈 등록됨");
		
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.addFilterBefore(new MyFilter3(),SecurityContextPersistenceFilter.class);
		http.headers().frameOptions().disable();
		http.csrf().disable(); // csrf 토큰 사용안함
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음
		.and()
		.addFilter(corsConfig.corsFilter()) 
		.formLogin()
		.usernameParameter("userEmail")
		.passwordParameter("userPassword")
		.disable()
		.httpBasic().disable() // 기본 인증방식 사용 안함
		.addFilter(new JwtAuthenticationFilter(authenticationManager()))
		.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
		.authorizeRequests()
		
		//권한 설정
		.antMatchers("/api/v1/user/**")
		.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/manager/**")
		.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		
		.anyRequest().permitAll();
		
	}
}
