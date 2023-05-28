package com.cos.mixin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.momim.Momim;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.Momim.MomimReqDto.CreateMomimReqDto;
import com.cos.mixin.service.MomimService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MomimController {

	private final MomimService momimService;
	
	@PostMapping("/momim/join") // 모임가입 
    public ResponseEntity<?> momimJoin(@AuthenticationPrincipal LoginUser loginUser) {
		// 모임가입을 해야한다
		
		
		return new ResponseEntity<>(new ResponseDto<>(1, "모임정보", null), HttpStatus.OK);
    }
	
	@GetMapping("/momim")
    public ResponseEntity<?> momimSerch(@AuthenticationPrincipal LoginUser loginUser) {
		List<Momim> momimList = momimService.모임정보();
		return new ResponseEntity<>(new ResponseDto<>(1, "모임정보", momimList), HttpStatus.OK);
    }
	
	@PostMapping("/momim/create")
    public ResponseEntity<?> momimCreate(@AuthenticationPrincipal LoginUser loginUser, @RequestBody @Valid CreateMomimReqDto createMomimReqDto, BindingResult bindingResult) {
        momimService.모임생성(createMomimReqDto,loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "모임 생성 성공", null), HttpStatus.CREATED);
    }
	
	@DeleteMapping("/momim/delete")
	public ResponseEntity<?> momimDelete(@PathVariable int imageId, @AuthenticationPrincipal LoginUser loginUser){
		
		return new ResponseEntity<>(new ResponseDto<>(1, "좋아요취소성공",null),HttpStatus.OK);
	}
	
}
