package com.cos.mixin.controller;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.user.User;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.user.UserReqDto.JoinReqDto;
import com.cos.mixin.dto.user.UserReqDto.SetCategoryReqDto;
import com.cos.mixin.dto.user.UserRespDto.JoinRespDto;
import com.cos.mixin.service.user.UserProfileService;
import com.cos.mixin.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
	private final UserService userService;

	
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }
    
    @PostMapping("/user/category")
    public ResponseEntity<?> setuserCategory(@AuthenticationPrincipal LoginUser loginUser, @RequestBody SetCategoryReqDto setCategoryReqDto) {
    	userService.유저카테고리등록(loginUser.getUser().getId(), setCategoryReqDto.getCategorys());
        return new ResponseEntity<>(new ResponseDto<>(1, "유저카테고리 설정 성공", null), HttpStatus.CREATED);
    }
    
    
    
    @GetMapping("/user")
    public ResponseEntity<?> profile(@AuthenticationPrincipal LoginUser loginUser) {
    	User userEntity = userService.유저정보보기(loginUser.getUser().getId());
    	return new ResponseEntity<>(new ResponseDto<>(1, "프로필정보", userEntity), HttpStatus.OK);
    }
    
    
    

    
   
}
