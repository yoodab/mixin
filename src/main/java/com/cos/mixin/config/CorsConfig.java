package com.cos.mixin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		System.out.println("CorsFilter실행됨");
		CorsConfiguration config = new CorsConfiguration();
		// 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정하는 것
		config.setAllowCredentials(true);
		// 모든 ip에 응답을 허용하겠다
		config.addAllowedOrigin("*");
		// 모든 Header에 응답을 허용하겠다
		config.addAllowedHeader("*");
		// 모든 요청에 응답을 허용하겠다
		config.addAllowedMethod("*");
		///api/로 시작하는 주소는 이 설정을 따라라
		source.registerCorsConfiguration("/api/**", config);
		return new CorsFilter(source);
	}
}
