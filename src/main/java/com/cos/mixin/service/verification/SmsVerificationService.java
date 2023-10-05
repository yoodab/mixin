package com.cos.mixin.service.verification;

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

import com.cos.mixin.domain.verification.email.FindEmailVerification;
import com.cos.mixin.domain.verification.email.JoinEmailVerification;
import com.cos.mixin.domain.verification.sms.FindSmsVerification;
import com.cos.mixin.domain.verification.sms.FindSmsVerificationRepository;
import com.cos.mixin.domain.verification.sms.JoinSmsVerification;
import com.cos.mixin.domain.verification.sms.JoinSmsVerificationRepository;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsReqDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsVeriDto;
import com.cos.mixin.dto.verification.VerificationRespDto.MessageRespDto;
import com.cos.mixin.dto.verification.VerificationVO;
import com.cos.mixin.handler.ex.CustomApiException;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SmsVerificationService {
	@Autowired
	private JoinSmsVerificationRepository joinSmsVerificationRepository;

	@Autowired
	private FindSmsVerificationRepository findSmsVerificationRepository;

	@Autowired
	private SmsService smsService;

	// 번호로 처음 인증번호 눌렀을때
	public MessageRespDto sendVerificationCode(SmsVeriDto smsVeriDto) throws RestClientException, InvalidKeyException,
			JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {

		LocalDateTime now = LocalDateTime.now();

		// 난수 생성
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			code += ran;
		}


		if ("join".equals(smsVeriDto.getVerificationType())) {
			JoinSmsVerification VerificationCode = joinSmsVerificationRepository
					.findByPhoneNumber(smsVeriDto.getUserPhoneNumber()).orElse(new JoinSmsVerification());
			smsVeriDto.setId(VerificationCode.getId());
			smsVeriDto.setRequestCount(VerificationCode.getRequestCount());
			smsVeriDto.setRequestTime(VerificationCode.getRequestTime());

		} else {

			System.out.println("============find==============");
			FindSmsVerification VerificationCode = findSmsVerificationRepository
					.findByPhoneNumber(smsVeriDto.getUserPhoneNumber()).orElse(new FindSmsVerification());
			System.out.println(VerificationCode.getId());
			smsVeriDto.setId(VerificationCode.getId());
			smsVeriDto.setRequestCount(VerificationCode.getRequestCount());
			smsVeriDto.setRequestTime(VerificationCode.getRequestTime());
		}

		// 인증 횟수 확인
		if (smsVeriDto.getRequestCount() >= 2) {
			// 인증실패 5회하고 다음 시도는 1일 지나고 가능
			if (smsVeriDto.getRequestTime() != null && smsVeriDto.getRequestTime().plusMinutes(1).isAfter(now)) {
				throw new CustomApiException("인증 횟수 초과");
			} else {
				System.out.println(smsVeriDto.getVerificationType());
				if ("join".equals(smsVeriDto.getVerificationType())) {
					joinSmsVerificationRepository.deleteById(smsVeriDto.getId());
					smsVeriDto.setRequestCount(0);
				} else {
					findSmsVerificationRepository.deleteById(smsVeriDto.getId());
					smsVeriDto.setRequestCount(0);
				}
			}
		}

		

		// 위의 경우가 아니면 DB에 저장
		smsVeriDto.setCode(code);
		smsVeriDto.setRequestTime(now);
		smsVeriDto.setRequestCount(smsVeriDto.getRequestCount() + 1);

		if ("join".equals(smsVeriDto.getVerificationType())) {
			joinSmsVerificationRepository.save(new JoinSmsVerification(smsVeriDto));
		} else {
			findSmsVerificationRepository.save(new FindSmsVerification(smsVeriDto));
		}

		String smsContent = VerificationVO.SMS_PREFIX + code + VerificationVO.SMS_POSTFIX;

		SmsDto smsDto = new SmsDto();
		smsDto.setTo(smsVeriDto.getUserPhoneNumber());
		smsDto.setContent(smsContent);

		// 메세지 전송
		MessageRespDto response = smsService.sendSms(smsDto);

		return response;
	}

	public boolean verifyCode(SmsCheckDto smsCheckDto) {
		LocalDateTime now = LocalDateTime.now();

		if ("join".equals(smsCheckDto.getVerificationType())) {
			Optional<JoinSmsVerification> VerificationCode = joinSmsVerificationRepository
					.findByPhoneNumber(smsCheckDto.getUserPhoneNumber());
			// 인증번호 제한 시간 3분 설정
			if (VerificationCode.isPresent() && VerificationCode.get().getRequestTime().plusMinutes(3).isAfter(now)) {
				if (VerificationCode.isPresent() && VerificationCode.get().getCode().equals(smsCheckDto.getCode())) {
					joinSmsVerificationRepository.delete(VerificationCode.get());
					return true;
				}

			}
		} else {
			Optional<FindSmsVerification> VerificationCode = findSmsVerificationRepository
					.findByPhoneNumber(smsCheckDto.getUserPhoneNumber());

			// 인증번호 제한 시간 3분 설정
			if (VerificationCode.isPresent() && VerificationCode.get().getRequestTime().plusMinutes(3).isAfter(now)) {
				if (VerificationCode.isPresent() && VerificationCode.get().getCode().equals(smsCheckDto.getCode())) {
					findSmsVerificationRepository.delete(VerificationCode.get());
					return true;
				}

			}
		}

		return false;

	}

}
