package com.cos.mixin.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
