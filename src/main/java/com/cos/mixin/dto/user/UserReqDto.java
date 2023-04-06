package com.cos.mixin.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UserReqDto {
	
	@Setter
    @Getter
    public static class LoginReqDto {
        private String userEmail;
        private String userPassword;
    }
	
	@Data
    public static class JoinReqDto {
    	// 아이디 비번 성별
    			@Size(min = 2,max = 20)
    			@NotBlank
    			private String userEmail;
    			@NotBlank
    			private String userPassword;
    			@NotBlank
    			private String userName;
    			
    			public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
    				return User.builder()
    						.userEmail(userEmail)
    						.userPassword(bCryptPasswordEncoder.encode(userPassword))
    						.userName(userName)
    						.role(UserEnum.CUSTOMER)
    						.build();
    			}
    }
	
	
}
