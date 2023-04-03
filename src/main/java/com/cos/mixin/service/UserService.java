package com.cos.mixin.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;
import com.cos.mixin.handler.ex.CustomValidationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User 회원수정(Long id, User user) {
		// 1. 영속화
				// 1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 익섹션 발동시킬께 orElseThrow()
				User userEntity = userRepository.findById(id).orElseThrow(() -> { return new CustomValidationException("찾을 수 없는 id입니다.");});

				// 변경할 내용 변경하기
				// 2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
				userEntity.setUserName(user.getUserName());
				
				String rawPassword = user.getUserPassword();
				String encPassword = bCryptPasswordEncoder.encode(rawPassword);
				
				userEntity.setUserPassword(encPassword);

		return userEntity;
	}
}
