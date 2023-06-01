package com.cos.mixin.dto.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserProfile;

import lombok.Data;

public class UserProfileDto {
	
	@Data
	public static class creUserProfileReqDto {
		// 역할
		private String userPosition;
		// 성격
		private String userPersonality;
		// 가치관
		private String userValues;
		// 별명
		private String userNickName;
		// 프로필사진
		private MultipartFile userProfilePicture;
		
		// 자기소개
		private String userIntroduceText;
		
		private int userSmileDegree;
		
		public UserProfile toEntity(String profilePictureUrl, User user) {
			return UserProfile.builder()
					.user(user)
					.position(userPosition)
					.personality(userPersonality)
					.userValues(userValues)
					.nickname(userNickName)
					.profilePictureUrl(profilePictureUrl)
					.introduction(userIntroduceText)
					.userSmileDegree(userSmileDegree)
					.build();
		}
    }
}
