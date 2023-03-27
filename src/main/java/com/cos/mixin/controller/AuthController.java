package com.cos.mixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.dto.auth.SignupDto;
import com.cos.mixin.dto.sms.MessageDTO;
import com.cos.mixin.dto.sms.SmsResponseDTO;
import com.cos.mixin.handler.ex.CustomValidationException;
import com.cos.mixin.service.AuthService;
import com.cos.mixin.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/http/post")
	public String postTest(User user) {
		System.out.println("id:"+user.getUserName() );
		System.out.println("id:"+user.getUserPhoneNumber() );
		System.out.println(user);
		return "post요청";
	}
	
	@GetMapping("/http/get")
	public String getTest() {
		return "get요청";
	}
	
	// 로그인
	@GetMapping("/auth/signin")
	public String Signin() {
		return "signin";
	}
	
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("api/v1/user/token")
	public String token() {
		return "<h1>token</h1>";
	}
	// user, manager, admin 권한 접근 가능
	@GetMapping("api/v1/user")
	public String user() {
		return "user";
	}
	// manager, admin 권한 접근 가능
	@GetMapping("api/v1/manager")
	public String manager() {
		return "manager";
	}
	// admin 권한 접근 가능
	@GetMapping("api/v1/admin")
	public String admin() {
		return "admin";
	}

	// 아이디 확인

	// 회원가입
	@PostMapping("/auth/signup")
	public String SignUp(@RequestBody @Valid SignupDto signupDto,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap =new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				
			}
			throw new CustomValidationException("유효성검사 실패함", errorMap);
		}else {
			User user = signupDto.toEntity();
			User userEntity = authService.회원가입(user);
			return "회원가입 완료";
		}
	}
	
	private final SmsService smsService;
	
	@GetMapping("/send")
	public String getSmsPage() {
		return "sendSms";
	}
	
	@PostMapping("/sms/send")
	public String sendSms(@RequestBody MessageDTO messageDto, Model model) throws JsonProcessingException, 
				RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		System.out.println("메세지 받음???????"+messageDto.getTo()+messageDto.getContent());
		SmsResponseDTO response = smsService.sendSms(messageDto);
		model.addAttribute("response", response);
		return "result";
	}
}
