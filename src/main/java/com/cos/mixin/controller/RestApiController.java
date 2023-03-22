package com.cos.mixin.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.model.User2;
import com.cos.mixin.model.User2Repository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RestApiController {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final User2Repository userRepository;
	
	
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	@PostMapping("token")
	public String token() {
		return "<h1>token</h1>";
	}
	
	@PostMapping("join")
	public String join(@RequestBody User2 user) {
		System.out.println("회원가입");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles("ROLE_USER");
		userRepository.save(user);
		return "회원가입완료";
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
}
