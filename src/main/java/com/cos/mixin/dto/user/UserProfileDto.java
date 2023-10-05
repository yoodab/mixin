package com.cos.mixin.dto.user;

import java.util.List;

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
		private List<String> userPersonalitys;
		// 가치관
		private String userValues;
		// 별명
		private String userNickName;
		// 프로필사진
		private MultipartFile userProfilePicture;

		// 자기소개
		private String userIntroduceText;

		private int userSmileDegree;

		public UserProfile toEntity(String userPersonality, String profilePictureUrl, User user) {
			return UserProfile.builder()
					.user(user)
					.position(userPosition)
					.personalitys(userPersonality)
					.userValues(userValues)
					.nickname(userNickName)
					.profilePictureUrl(profilePictureUrl)
					.introduction(userIntroduceText)
					.userSmileDegree(userSmileDegree)
					.build();
		}
		
		public UserProfile toEntity(String userPersonality, User user) {
			return UserProfile.builder()
					.user(user)
					.position(userPosition)
					.personalitys(userPersonality)
					.userValues(userValues)
					.nickname(userNickName)
					.introduction(userIntroduceText)
					.userSmileDegree(userSmileDegree)
					.build();
		}

	}

	@Data
	public static class UpdateUserProfileDTO {
		// 역할
		private String userPosition;
		// 성격
		private List<String> userPersonalitys;
		// 가치관
		private String userValues;
		// 별명
		private String userNickName;
		// 프로필 사진
		private MultipartFile userProfilePicture;
		// 자기 소개
		private String userIntroduceText;
	}
}
