package com.cos.mixin.service.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserProfile;
import com.cos.mixin.domain.user.UserProfileRepository;
import com.cos.mixin.domain.user.UserRepository;
import com.cos.mixin.dto.user.UserProfileDto.UpdateUserProfileDTO;
import com.cos.mixin.dto.user.UserProfileDto.creUserProfileReqDto;
import com.cos.mixin.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserProfileService {

	private final UserRepository userRepository;
	private final UserProfileRepository userProfileRepository;

	@Value("${file.profilepath}")
	private String uploadFolder;

	@Transactional
	public UserProfile 유저프로필생성(creUserProfileReqDto creProfileReqDto, LoginUser loginUser) {
		User userEntity = userRepository.findById(loginUser.getUser().getId())
				.orElseThrow(() -> new CustomApiException("User not found"));

		String userPersonalitys = String.join(",", creProfileReqDto.getUserPersonalitys());
		
		UUID uuid = UUID.randomUUID();

		String imageFileName = null;
		UserProfile userProfileEntity = null;
		if (creProfileReqDto.getUserProfilePicture() != null) {
			imageFileName = uuid + "_" + creProfileReqDto.getUserProfilePicture().getOriginalFilename();

			Path imageFilePath = Paths.get(uploadFolder + imageFileName);

			try {
				Files.write(imageFilePath, creProfileReqDto.getUserProfilePicture().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			userProfileEntity = creProfileReqDto.toEntity(userPersonalitys, imageFileName, userEntity);
		}else {
			userProfileEntity = creProfileReqDto.toEntity(userPersonalitys, userEntity);
		}
		
		
			
		UserProfile sUserProfileEntity = userProfileRepository.save(userProfileEntity);

		return sUserProfileEntity;
	}
	
	public UserProfile 유저프로필사진(MultipartFile file, LoginUser loginUser) {
		
		UserProfile userprofile = userProfileRepository.findByUser(loginUser.getUser()).orElseThrow(() -> new CustomApiException("User not found"));
		UUID uuid = UUID.randomUUID();

		String imageFileName = null;
		if (file != null) {
			imageFileName = uuid + "_" + file.getOriginalFilename();

			Path imageFilePath = Paths.get(uploadFolder + imageFileName);

			try {
				Files.write(imageFilePath, file.getBytes());
				userprofile.setProfilePictureUrl(imageFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			UserProfile ProfileEntity = userProfileRepository.save(userprofile);
			return ProfileEntity;
		}
		
		
		
		return null;
	}
	

	public UserProfile 유저프로필수정(UpdateUserProfileDTO updateDto, LoginUser loginUser) {
		User userEntity = userRepository.findById(loginUser.getUser().getId())
				.orElseThrow(() -> new CustomApiException("User not found"));
		
        UserProfile userProfile = userProfileRepository.findByUser(userEntity)
                .orElseThrow(() -> new CustomApiException("User profile not found"));

        
        
        userProfile.setPosition(updateDto.getUserPosition());
        String userPersonalitys = String.join(",", updateDto.getUserPersonalitys());
        userProfile.setPersonalitys(userPersonalitys);
        userProfile.setUserValues(updateDto.getUserValues());
        userProfile.setNickname(updateDto.getUserNickName());
        userProfile.setIntroduction(updateDto.getUserIntroduceText());

        // Handle profile picture update
        MultipartFile profilePictureFile = updateDto.getUserProfilePicture();
        if (profilePictureFile != null && !profilePictureFile.isEmpty()) {
        	UUID uuid = UUID.randomUUID();
        	String imageFileName = null;
        	imageFileName = uuid + "_" + updateDto.getUserProfilePicture().getOriginalFilename();

			Path imageFilePath = Paths.get(uploadFolder + imageFileName);

			try {
				Files.write(imageFilePath, updateDto.getUserProfilePicture().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
 //           String profilePictureUrl = fileUploadService.uploadFile(profilePictureFile);
  //          userProfile.setProfilePictureUrl(profilePictureUrl);
        }

        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);
        return updatedUserProfile;
    }


	public UserProfile 유저프로필보기(LoginUser loginUser) {
		User userEntity = userRepository.findById(loginUser.getUser().getId())
				.orElseThrow(() -> new CustomApiException("User not found"));
		Optional<UserProfile> userProfile = userProfileRepository.findByUser(userEntity);
		UserProfile userprofileEntity = userProfile.get();
		userprofileEntity.setUser(null);
		return userprofileEntity;
	}
}
