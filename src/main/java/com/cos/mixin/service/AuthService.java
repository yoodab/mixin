package com.cos.mixin.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원가입(User user) {
		String rawPassword = user.getUserPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setUserPassword(encPassword);
		user.setRoles("ROLE_USER");
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
