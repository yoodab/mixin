package com.cos.mixin.service.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.user.UserProfile;
import com.cos.mixin.domain.user.UserProfileRepository;
import com.cos.mixin.dto.user.UserProfileDto.creUserProfileReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserProfileService {

	private final UserProfileRepository userProfileRepository;

	@Value("${file.profilepath}")
	private String uploadFolder;

	@Transactional
	public UserProfile 유저프로필생성(creUserProfileReqDto creProfileReqDto, LoginUser loginUser) {
		UUID uuid = UUID.randomUUID();

		String imageFileName = null;
		if (creProfileReqDto.getUserProfilePicture() != null) {
			imageFileName = uuid + "_" + creProfileReqDto.getUserProfilePicture().getOriginalFilename();

			Path imageFilePath = Paths.get(uploadFolder + imageFileName);

			try {
				Files.write(imageFilePath, creProfileReqDto.getUserProfilePicture().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		UserProfile userProfileEntity = creProfileReqDto.toEntity(imageFileName, loginUser.getUser());

		UserProfile sUserProfileEntity = userProfileRepository.save(userProfileEntity);

		return sUserProfileEntity;
	}

	public void 유저프로필수정() {

	}

	public void 유저프로필보기() {

	}
}
