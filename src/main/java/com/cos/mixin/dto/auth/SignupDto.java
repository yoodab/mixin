package com.cos.mixin.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.mixin.domain.user.User;


import lombok.Data;

@Data
public class SignupDto {
	//학교 학번 학교인증 학과
	
		// 아이디 비번 성별
		@Size(min = 2,max = 20)
		@NotBlank
		private String userEmail;
		@NotBlank
		private String userPassword;
		@NotBlank
		private String userName;
		
		public User toEntity() {
			return User.builder()
					.userEmail(userEmail)
					.userPassword(userPassword)
					.userName(userName)
					.build();
		}
}
