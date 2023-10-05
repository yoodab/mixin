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

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;
import com.cos.mixin.domain.verification.email.FindEmailVerification;
import com.cos.mixin.domain.verification.email.FindEmailVerificationRepository;
import com.cos.mixin.domain.verification.email.JoinEmailVerification;
import com.cos.mixin.domain.verification.email.JoinEmailVerificationRepository;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailDto;
import com.cos.mixin.dto.verification.VerificationReqDto.EmailReqDto;
import com.cos.mixin.dto.verification.VerificationReqDto.MailReqDto;
import com.cos.mixin.dto.verification.VerificationRespDto.EmailRespDto;
import com.cos.mixin.dto.verification.VerificationVO;
import com.cos.mixin.handler.ex.CustomApiException;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class EmailVerificationService {
	@Autowired
	private JoinEmailVerificationRepository joinEmailVerificationRepository;
	
	@Autowired
	private FindEmailVerificationRepository findEmailVerificationRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	public Boolean checkUser(EmailDto emailDto) {
		Optional<User> VerificationCode = userRepository.findByUserEmail(emailDto.getUserEmail());
		return !VerificationCode.isEmpty();
	}

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
		
		EmailReqDto emailReqDto = new EmailReqDto();
		
		if ("join".equals(emailDto.getVerificationType())) {
		    JoinEmailVerification VerificationCode = joinEmailVerificationRepository.findByUserEmail(emailDto.getUserEmail())
		        .orElse(new JoinEmailVerification());
		    emailReqDto.setRequestCount(VerificationCode.getRequestCount());
		    emailReqDto.setUserEmail(VerificationCode.getUserEmail());
		    emailReqDto.setRequestTime(VerificationCode.getRequestTime());
		    emailReqDto.setId(VerificationCode.getId());
		} else {
		    FindEmailVerification VerificationCode = findEmailVerificationRepository.findByUserEmail(emailDto.getUserEmail())
		        .orElse(new FindEmailVerification());
		    emailReqDto.setRequestCount(VerificationCode.getRequestCount());
		    emailReqDto.setUserEmail(VerificationCode.getUserEmail());
		    emailReqDto.setRequestTime(VerificationCode.getRequestTime());	
		    emailReqDto.setId(VerificationCode.getId());
		}
		
		// 인증 횟수 확인 5번까지 가능
		if (emailReqDto.getRequestCount() >= 5) {
			// 인증실패 5회 하고 다음 시도는 1일 지나고 가능
			if (emailReqDto.getRequestTime() != null	&& emailReqDto.getRequestTime().plusMinutes(3).isAfter(now)) {
				throw new CustomApiException("인증 횟수 초과");
			} else {
				if ("join".equals(emailDto.getVerificationType())) {
					joinEmailVerificationRepository.deleteById(emailReqDto.getId());
					emailReqDto.setRequestCount(0);
				} else {
					findEmailVerificationRepository.deleteById(emailReqDto.getId());
					emailReqDto.setRequestCount(0);
				}
				
			}
		}
		// 위의 경우가 아니면 DB에 저장
		emailReqDto.setRequestCount(emailReqDto.getRequestCount() + 1);
		emailReqDto.setUserEmail(emailDto.getUserEmail());
		emailReqDto.setCode(code);
		emailReqDto.setRequestTime(now);
		

		if ("join".equals(emailDto.getVerificationType())) {
			joinEmailVerificationRepository.save(new JoinEmailVerification(emailReqDto));
		} else {
			findEmailVerificationRepository.save(new FindEmailVerification(emailReqDto));
		}
		
		

		String emailContent = VerificationVO.EMAIL_PREFIX + code + VerificationVO.EMAIL_POSTFIX;

		MailReqDto mailReqDto = new MailReqDto();
		mailReqDto.setContent(emailContent);
		mailReqDto.setUserEmail(emailDto.getUserEmail());
		mailReqDto.setTitle(VerificationVO.EMAIL_TITLE);

		String statusName = emailService.sendMail(mailReqDto);
		EmailRespDto emailRespDto = new EmailRespDto();
		emailRespDto.setRequestCount(emailReqDto.getRequestCount());
		emailRespDto.setRequestTime(now);
		emailRespDto.setStatusName(statusName);
		// 메세지 전송

		return emailRespDto;
	}

	public boolean verifyCode(EmailCheckDto emailCheckDto) {
		
		LocalDateTime now = LocalDateTime.now();
		if ("join".equals(emailCheckDto.getVerificationType())) {
			Optional<JoinEmailVerification> VerificationCode = joinEmailVerificationRepository
					.findByUserEmail(emailCheckDto.getUserEmail());
			// 인증번호 제한 시간 3분 설정
			if (VerificationCode.isPresent() && VerificationCode.get().getRequestTime().plusMinutes(3).isAfter(now)) {
				if (VerificationCode.isPresent() && VerificationCode.get().getCode().equals(emailCheckDto.getCode())) {
					joinEmailVerificationRepository.delete(VerificationCode.get());
					return true;
				}

			}
		} else {
			Optional<FindEmailVerification> VerificationCode = findEmailVerificationRepository
					.findByUserEmail(emailCheckDto.getUserEmail());
			
			// 인증번호 제한 시간 3분 설정
			if (VerificationCode.isPresent() && VerificationCode.get().getRequestTime().plusMinutes(3).isAfter(now)) {
				if (VerificationCode.isPresent() && VerificationCode.get().getCode().equals(emailCheckDto.getCode())) {
					findEmailVerificationRepository.delete(VerificationCode.get());
					return true;
				}

			}
		}
		
		

		
		return false;

	}
}
