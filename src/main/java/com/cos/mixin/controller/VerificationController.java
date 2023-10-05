package com.cos.mixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.user.UserRespDto.FindIdRespDto;
import com.cos.mixin.dto.user.UserRespDto.FindPasswordRespDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailDto;
import com.cos.mixin.dto.verification.VerificationReqDto.FindPasswordDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsVeriDto;
import com.cos.mixin.dto.verification.VerificationRespDto.EmailRespDto;
import com.cos.mixin.dto.verification.VerificationRespDto.MessageRespDto;
import com.cos.mixin.service.user.UserService;
import com.cos.mixin.service.verification.EmailVerificationService;
import com.cos.mixin.service.verification.SmsVerificationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class VerificationController {

	private final SmsVerificationService smsVerificationService;
	private final EmailVerificationService emailVerificationService;
	private final UserService userService;

	@PostMapping("/join/sms/send")
	public ResponseEntity<?> sendSms(@RequestBody SmsVeriDto smsVeriDto)
			throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		smsVeriDto.setVerificationType("join");
		MessageRespDto response = smsVerificationService.sendVerificationCode(smsVeriDto);
		return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증번호 전송 성공", response), HttpStatus.CREATED);
	}

	@PostMapping("/join/sms/check")
	public ResponseEntity<?> checkSmsNum(@RequestBody SmsCheckDto smsCheckDto) {

		smsCheckDto.setVerificationType("join");
		if (smsVerificationService.verifyCode(smsCheckDto)) {
			return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증성공", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패", null), HttpStatus.CREATED);
		}
	}

	@PostMapping("/join/email/send")
	public ResponseEntity<?> joinSendEmail(@RequestBody EmailDto emailDto)
			throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		emailDto.setVerificationType("join");
		System.out.println("==========================");
		System.out.println(emailDto.getUserEmail());
		EmailRespDto emailRespDto = emailVerificationService.sendVerificationCode(emailDto);
		return new ResponseEntity<>(new ResponseDto<>(1, "email 인증번호 전송 성공", emailRespDto), HttpStatus.CREATED);
	}

	@PostMapping("/join/email/check")
	public ResponseEntity<?> joinCheckEmailNum(@RequestBody EmailCheckDto emailCheckDto) {
		emailCheckDto.setVerificationType("join");
		if (emailVerificationService.verifyCode(emailCheckDto)) {
			return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증성공", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패", null), HttpStatus.NOT_FOUND);
		}
	}

	// 아이디 찾기 번호로 인증번호 전송
	@PostMapping("/find/id")
	public ResponseEntity<?> findId(@RequestBody SmsVeriDto smsVeriDto)
			throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		smsVeriDto.setVerificationType("find");
		Optional<User> userEntity = userService.유저번호검색(smsVeriDto.getUserPhoneNumber());
		System.out.println(userEntity.get().getUserName());
		System.out.println(smsVeriDto.getUserName());
		System.out.println(userEntity.get().getUserName()+ smsVeriDto.getUserName()+1);
		if (userEntity.isEmpty()) {
			System.out.println(1);
			return new ResponseEntity<>(new ResponseDto<>(-1, "없는 유저입니다", null), HttpStatus.NOT_FOUND);
		} else {
			if(userEntity.get().getUserName().equals(smsVeriDto.getUserName())) {
				smsVeriDto.setVerificationType("find");
				MessageRespDto response = smsVerificationService.sendVerificationCode(smsVeriDto);
				return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증번호전송 성공", response), HttpStatus.CREATED);
			}else {
				System.out.println(2);
				return new ResponseEntity<>(new ResponseDto<>(-1, "없는 유저입니다", null), HttpStatus.NOT_FOUND);
			}
			
			
		}
	}

	// 아이디 찾기 인증번호 확인
	@PostMapping("/find/id/check")
	public ResponseEntity<?> findIdCheck(@RequestBody SmsCheckDto smsCheckDto) {
		smsCheckDto.setVerificationType("find");
		if (smsVerificationService.verifyCode(smsCheckDto)) {
			Optional<User> userEntity = userService.유저번호검색(smsCheckDto.getUserPhoneNumber());
			FindPasswordRespDto findPasswordDto = new FindPasswordRespDto();
			findPasswordDto.setUserEmail(userEntity.get().getUserEmail());
			findPasswordDto.setUserPhoneNumber(userEntity.get().getUserPhoneNumber());

			return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증성공", findPasswordDto), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패", null), HttpStatus.NOT_FOUND);
		}
	}

	// 비번찾기 1단계
	@PostMapping("/find/password")
	public ResponseEntity<?> findPassword(@RequestBody FindPasswordDto findPasswordDto) {
		Optional<User> userEntity = userService.유저번호검색(findPasswordDto.getEmailOrPhoneNum());
		System.out.println(findPasswordDto.getEmailOrPhoneNum());
		if (userEntity.isEmpty()) {
			userEntity = userService.유저이메일검색(findPasswordDto.getEmailOrPhoneNum());
		}
		if (userEntity.isEmpty()) {
			return new ResponseEntity<>(new ResponseDto<>(-1, "없는 유저입니다.", null), HttpStatus.NOT_FOUND);
		}
		FindPasswordRespDto findPasswordRespDto = new FindPasswordRespDto();
		findPasswordRespDto.setUserEmail(userEntity.get().getUserEmail());
		findPasswordRespDto.setUserPhoneNumber(userEntity.get().getUserPhoneNumber());
		return new ResponseEntity<>(new ResponseDto<>(1, "비밀번호찾기가능", findPasswordRespDto), HttpStatus.CREATED);
	}
	
	// 문자로 인증시작
	@PostMapping("/find/password/sms")
	public ResponseEntity<?> findSendSms(@RequestBody SmsVeriDto smsVeriDto)
			throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {

		smsVeriDto.setVerificationType("find");
		MessageRespDto response = smsVerificationService.sendVerificationCode(smsVeriDto);
		return new ResponseEntity<>(new ResponseDto<>(1, "sms 인증번호전송 성공", response), HttpStatus.CREATED);

	}

	// 이메일로 인증시작
	@PostMapping("/find/password/email")
	public ResponseEntity<?> findSendEmail(@RequestBody EmailDto emailDto)
			throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		emailDto.setVerificationType("find");
		EmailRespDto emailRespDto = emailVerificationService.sendVerificationCode(emailDto);
		return new ResponseEntity<>(new ResponseDto<>(1, "email 인증번호 전송 성공", emailRespDto), HttpStatus.CREATED);
	}
	
	// 이메일 인증 확인
	@PostMapping("/find/password/email/check")
	public ResponseEntity<?> findCheckEmailNum(@RequestBody EmailCheckDto emailCheckDto) {
		emailCheckDto.setVerificationType("find");
		if (emailVerificationService.verifyCode(emailCheckDto)) {
			return new ResponseEntity<>(new ResponseDto<>(1, "비밀번호 변경가능", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패", null), HttpStatus.NOT_FOUND);
		}
	}
	// 문자 인증 확인
	@PostMapping("/find/password/sms/check")
	public ResponseEntity<?> findCheckSmsNum(@RequestBody SmsCheckDto smsCheckDto) {
		smsCheckDto.setVerificationType("find");
		if (smsVerificationService.verifyCode(smsCheckDto)) {
			return new ResponseEntity<>(new ResponseDto<>(1, "비밀번호 변경가능", null), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(new ResponseDto<>(-1, "sms 인증실패", null), HttpStatus.NOT_FOUND);
		}
	}

}
