package com.cos.mixin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.user.UserProfile;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.user.UserProfileDto.UpdateUserProfileDTO;
import com.cos.mixin.dto.user.UserProfileDto.creUserProfileReqDto;
import com.cos.mixin.service.user.UserProfileService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserProfileController {
	
	private final UserProfileService userProfileService;
	
    @GetMapping("/user/profile")
    public ResponseEntity<?> userProfileCheck(@AuthenticationPrincipal LoginUser loginUser) {
    	UserProfile userProfile = userProfileService.유저프로필보기(loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "유저프로필 보기 성공", userProfile), HttpStatus.CREATED);
    }
    
    @PostMapping("/ad/user/profile")
    public ResponseEntity<?> creUserProfile(@AuthenticationPrincipal LoginUser loginUser,@RequestBody creUserProfileReqDto crProfileReqDto) {
    	UserProfile userprofile= userProfileService.유저프로필생성(crProfileReqDto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "유저프로필 생성 성공", userprofile), HttpStatus.CREATED);
    }
    
    
    
    
    @PutMapping("/user/profile/onlyimage")
    public ResponseEntity<?> creUserProfileImage(@AuthenticationPrincipal LoginUser loginUser,@RequestPart("file") MultipartFile file,HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
    	System.out.println("==============");
    	String contentType = request.getHeader("Content-Type");
    	System.out.println("Content-Type: " + contentType);
    	System.out.println("==============");
    	System.out.println(file);
    	userProfileService.유저프로필사진(file, loginUser);
    	
    	
        return new ResponseEntity<>(new ResponseDto<>(1, "유저프로필 생성 성공", null), HttpStatus.CREATED);
    }


    
    
    @PutMapping("/user/profile")
    public ResponseEntity<?> modUserProfile(@AuthenticationPrincipal LoginUser loginUser,@RequestBody UpdateUserProfileDTO updateUserProfileDTO) {
    	userProfileService.유저프로필수정(updateUserProfileDTO, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "유저프로필 수정 성공", null), HttpStatus.CREATED);
    }
}
