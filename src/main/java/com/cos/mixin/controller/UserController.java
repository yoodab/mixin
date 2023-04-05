package com.cos.mixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.mail.MailDto;
import com.cos.mixin.dto.sms.MessageDTO;
import com.cos.mixin.dto.sms.SmsResponseDTO;
import com.cos.mixin.dto.user.UserReqDto.JoinReqDto;
import com.cos.mixin.dto.user.UserRespDto.JoinRespDto;
import com.cos.mixin.service.EmailService;
import com.cos.mixin.service.SmsService;
import com.cos.mixin.service.UserService;
import com.cos.mixin.service.VerificationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
	private final UserService userService;
	private final VerificationService verificationService;
	private final EmailService emailService;
	
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult) {
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }
    
    
    @PostMapping("/mail/send")
    public String sendMail(@RequestBody MailDto mailDto) {
        emailService.sendMail(mailDto);
        System.out.println("메일 전송 완료");
        return "메일 전송 완료";
    }
    
    @PostMapping("/sms/send")
	public ResponseEntity<?> sendSms(@RequestBody MessageDTO messageDto, Model model) throws JsonProcessingException, 
				RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
    	
		SmsResponseDTO response = verificationService.sendVerificationCode(messageDto.getTo());
		model.addAttribute("response", response);
		return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증번호 전송 성공",response), HttpStatus.CREATED);
	}
    
    
    @PostMapping("/sms/check")
	public ResponseEntity<?> checkNum(@RequestBody MessageDTO messageDto ) {
    	if(verificationService.verifyCode(messageDto.getTo(), messageDto.getContext())) {
    		return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증성공",null), HttpStatus.CREATED);
    	}else {
    		return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패",null), HttpStatus.BAD_REQUEST);
    	}
	}
}
