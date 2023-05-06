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
import com.cos.mixin.dto.user.UserRespDto.JoinRespDto;
import com.cos.mixin.service.UserService;

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
    
    
    @GetMapping("/user/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal LoginUser loginUser) {
    	System.out.println(loginUser.getUser().getId());
    	User userEntity = userService.프로필확인(loginUser.getUser().getId());
    	return new ResponseEntity<>(new ResponseDto<>(1, "프로필정보", userEntity), HttpStatus.OK);
    }
    
   
}
