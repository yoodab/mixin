package com.cos.mixin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.user.UserProfile;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.user.UserProfileDto.creUserProfileReqDto;
import com.cos.mixin.service.user.UserProfileService;
import com.cos.mixin.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserProfileController {
	
	private final UserProfileService userProfileService;
	
    @GetMapping("/user/profile")
    public ResponseEntity<?> userProfileCheck(@AuthenticationPrincipal LoginUser loginUser) {
    	userProfileService.유저프로필보기();
        return new ResponseEntity<>(new ResponseDto<>(1, "유저프로필 설정 성공", null), HttpStatus.CREATED);
    }
    
    @PostMapping("/user/profile")
    public ResponseEntity<?> creUserProfile(@AuthenticationPrincipal LoginUser loginUser,@RequestBody creUserProfileReqDto crProfileReqDto) {
    	UserProfile userprofile= userProfileService.유저프로필생성(crProfileReqDto, loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "유저프로필 생성 성공", null), HttpStatus.CREATED);
    }
}
