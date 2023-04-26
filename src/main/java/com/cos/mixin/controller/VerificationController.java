package com.cos.mixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsDto;
import com.cos.mixin.dto.verification.VerificationRespDto.EmailRespDto;
import com.cos.mixin.dto.verification.VerificationRespDto.MessageRespDto;
import com.cos.mixin.service.EmailVerificationService;
import com.cos.mixin.service.SmsVerificationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class VerificationController {
	
	private final SmsVerificationService smsVerificationService;
	private final EmailVerificationService emailVerificationService;
	
	 @PostMapping("/sms/send")
		public ResponseEntity<?> sendSms(@RequestBody SmsDto smsDto) throws JsonProcessingException, 
					RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
			MessageRespDto response = smsVerificationService.sendVerificationCode(smsDto.getTo());
			return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증번호 전송 성공",response), HttpStatus.CREATED);
		}
	    
	    
	    @PostMapping("/sms/check")
		public ResponseEntity<?> checkSmsNum(@RequestBody SmsCheckDto smsCheckDto ) {
	    	if(smsVerificationService.verifyCode(smsCheckDto)) {
	    		return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증성공",null), HttpStatus.CREATED);
	    	}else {
	    		return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패",null), HttpStatus.BAD_REQUEST);
	    	}
		}
	    
	    
	    
	    @PostMapping("/email/send")
		public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto) throws JsonProcessingException, 
					RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
	    	System.out.println("==========================");
	    	System.out.println(emailDto.getUserEmail());
	    	EmailRespDto emailRespDto=emailVerificationService.sendVerificationCode(emailDto);
			return new ResponseEntity<>(new ResponseDto<>(1, "email 인증번호 전송 성공",emailRespDto), HttpStatus.CREATED);
		}
	    
	    
	    @PostMapping("/email/check")
		public ResponseEntity<?> checkEmailNum(@RequestBody EmailCheckDto emailCheckDto ) {
	    	if(emailVerificationService.verifyCode(emailCheckDto)) {
	    		return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증성공",null), HttpStatus.CREATED);
	    	}else {
	    		return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패",null), HttpStatus.BAD_REQUEST);
	    	}
		}
}
