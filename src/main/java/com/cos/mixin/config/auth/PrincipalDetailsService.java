package com.cos.mixin.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;


import lombok.RequiredArgsConstructor;


// http://localhost:8080/login 요청시 실행됨
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUserEmail(username);
		return new PrincipalDetails(userEntity);
	}
	
	
}
