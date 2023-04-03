package com.cos.mixin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	// 업데이트 관련
	@GetMapping("/user/{id}/update")
	public String updatePage(@PathVariable int id , @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("세션 정보 : "+principalDetails);
		return null;
	}

}
