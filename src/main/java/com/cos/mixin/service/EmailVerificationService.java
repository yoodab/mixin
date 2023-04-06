package com.cos.mixin.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.cos.mixin.domain.verification.email.EmailVerification;
import com.cos.mixin.domain.verification.email.EmailVerificationRepository;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailDto;
import com.cos.mixin.dto.verification.VerificationReqDto.MailReqDto;
import com.cos.mixin.dto.verification.VerificationRespDto.EmailRespDto;
import com.cos.mixin.dto.verification.VerificationVO;
import com.cos.mixin.handler.ex.CustomApiException;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class EmailVerificationService {
	@Autowired
	private EmailVerificationRepository emailVerificationCodeRepository;

	@Autowired
	private EmailService emailService;

	// 번호로 처음 인증번호 눌렀을때
	public EmailRespDto sendVerificationCode(EmailDto emailDto) throws RestClientException, InvalidKeyException,
			JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
		LocalDateTime now = LocalDateTime.now();

		// 난수 생성
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			code += ran;
		}

		EmailVerification VerificationCode = emailVerificationCodeRepository.findByUserEmail(emailDto.getUserEmail())	.orElse(new EmailVerification());
		// 인증 횟수 확인 5번까지 가능
		if (VerificationCode.getRequestCount() >= 2) {
			// 인증실패 5회 하고 다음 시도는 1일 지나고 가능
			if (VerificationCode.getRequestTime() != null	&& VerificationCode.getRequestTime().plusMinutes(1).isAfter(now)) {
				throw new CustomApiException("인증 횟수 초과");
			} else {
				emailVerificationCodeRepository.delete(VerificationCode);
				VerificationCode.setRequestCount(0);
			}
		}

		// 위의 경우가 아니면 DB에 저장
		VerificationCode.setRequestCount(VerificationCode.getRequestCount() + 1);
		VerificationCode.setUserEmail(emailDto.getUserEmail());
		VerificationCode.setCode(code);
		VerificationCode.setRequestTime(now);

		emailVerificationCodeRepository.save(VerificationCode);

		String emailContent = VerificationVO.EMAIL_PREFIX + code + VerificationVO.EMAIL_PREFIX;

		MailReqDto mailReqDto = new MailReqDto();
		mailReqDto.setContent(emailContent);
		mailReqDto.setUserEmail(emailDto.getUserEmail());
		mailReqDto.setTitle(VerificationVO.EMAIL_TITLE);

		String statusName = emailService.sendMail(mailReqDto);
		EmailRespDto emailRespDto = new EmailRespDto();
		emailRespDto.setRequestCount(VerificationCode.getRequestCount());
		emailRespDto.setRequestTime(now);
		emailRespDto.setStatusName(statusName);
		// 메세지 전송

		return emailRespDto;
	}

	public boolean verifyCode(EmailCheckDto emailCheckDto) {
		Optional<EmailVerification> VerificationCode = emailVerificationCodeRepository
				.findByUserEmail(emailCheckDto.getUserEmail());
		LocalDateTime now = LocalDateTime.now();

		// 인증번호 제한 시간 3분 설정
		if (VerificationCode.isPresent() && VerificationCode.get().getRequestTime().plusMinutes(3).isAfter(now)) {
			if (VerificationCode.isPresent() && VerificationCode.get().getCode().equals(emailCheckDto.getCode())) {
				emailVerificationCodeRepository.delete(VerificationCode.get());
				return true;
			}

		}
		return false;

	}
}
