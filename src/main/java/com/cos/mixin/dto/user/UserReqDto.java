package com.cos.mixin.dto.user;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.mixin.domain.user.User;

import lombok.Data;
import lombok.Setter;

public class UserReqDto {
	
	@Data
	public static class LoginReqDto {
        private String userEmail;
        private String userPassword;
    }
	
	@Data
    public static class SetCategoryReqDto {
		private List<String> categorys;
    }
	@Data
	public static class UpdateUserPasswordDto {
		private String userEmail;
		private String userPassword;
	}
	
	@Data
    public static class JoinReqDto {
    	// 아이디 비번 성별
    			@Size(min = 2,max = 40)
    			@NotBlank
    			private String userEmail;
    			
    			@NotBlank
    			private String userPassword;
    			
    			@NotBlank
    			private String userName;
    			
    			private String agradInfrm;
    			// 성별
    			private String userGender;
    			// 번호
    			private String userPhoneNumber;

    			private String userCarrier;
    			// 학교명
    			private String userUniversity;

    			// 학번
    			private String userStudentId;

    			// 학과
    			private String userDepartment;
    			
    			public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
    				return User.builder()
    						.userEmail(userEmail)
    						.userPassword(bCryptPasswordEncoder.encode(userPassword))
    						.userName(userName)
    						.agradInfrm(agradInfrm)
    						.userGender(userGender)
    						.userPhoneNumber(userPhoneNumber)
    						.userCarrier(userCarrier)
    						.userUniversity(userUniversity)
    						.userStudnetId(userStudentId)
    						.userDepartment(userDepartment)
    						.build();
    			}
    			
    }
	
	
}
