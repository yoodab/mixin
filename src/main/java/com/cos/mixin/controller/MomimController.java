package com.cos.mixin.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.momim.Momim;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.Momim.MomimReqDto.CreateMomimReqDto;
import com.cos.mixin.dto.Momim.MomimReqDto.JoinMomimReqDto;
import com.cos.mixin.service.momim.MomimService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MomimController {

	private final MomimService momimService;
	
	@PostMapping("/momim/join") // 모임가입 
    public ResponseEntity<?> momimJoin(@RequestBody JoinMomimReqDto joinMomimReqDto,@AuthenticationPrincipal LoginUser loginUser) {
		// 모임가입을 해야한다
		momimService.모임가입(joinMomimReqDto, loginUser);
		return new ResponseEntity<>(new ResponseDto<>(1, "모임정보", null), HttpStatus.OK);
    }
	
	@GetMapping("/momim")
    public ResponseEntity<?> momimSerch(@AuthenticationPrincipal LoginUser loginUser) {
		List<Momim> momimList = momimService.내모임정보(loginUser);
		return new ResponseEntity<>(new ResponseDto<>(1, "모임정보", null), HttpStatus.OK);
    }
	
	@PostMapping("/momim/create")
    public ResponseEntity<?> momimCreate(@AuthenticationPrincipal LoginUser loginUser,@RequestPart MultipartFile file,
    		@RequestPart CreateMomimReqDto data) {
        System.out.println(file);
        data.setMomimThumbnailFile(file);
		momimService.모임생성(data,loginUser);
        return new ResponseEntity<>(new ResponseDto<>(1, "모임 생성 성공", null), HttpStatus.CREATED);
    }
	
	// Delete 는 body에 데이터 못넣고 주소에 넣어야 함
	@DeleteMapping("/momim/delete/{momimId}")
	public ResponseEntity<?> momimDelete(@PathVariable Long momimId, @AuthenticationPrincipal LoginUser loginUser){
		momimService.모임삭제(momimId,loginUser);
		return new ResponseEntity<>(new ResponseDto<>(1, "모임 삭제 성공",null),HttpStatus.OK);
	}
	
}
