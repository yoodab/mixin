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

import com.cos.mixin.domain.smsVerification.SmsVerification;
import com.cos.mixin.domain.smsVerification.SmsVerificationRepository;
import com.cos.mixin.dto.sms.MessageDTO;
import com.cos.mixin.dto.sms.SmsResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class VerificationService {
    @Autowired
    private SmsVerificationRepository smsVerificationCodeRepository;
    
    @Autowired
    private SmsService smsService;

    // 번호로 처음 인증번호 눌렀을때
    public SmsResponseDTO sendVerificationCode(String phoneNumber) throws RestClientException, InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        SmsVerification VerificationCode = new SmsVerification();
        
        // 인증 횟수 확인
        VerificationCode = smsVerificationCodeRepository.findByPhoneNumber(phoneNumber).orElse(new SmsVerification());
        if (VerificationCode.getRequestCount() >= 5) {
            throw new RuntimeException("인증 횟수 초과");
        }
        // 인증 시간 확인
        VerificationCode = smsVerificationCodeRepository.findById(phoneNumber).orElse(new SmsVerification());
        if (VerificationCode.getRequestTime() != null && VerificationCode.getRequestTime().plusMinutes(5).isAfter(now)) {
            throw new RuntimeException("사용 가능 기한 초과");
        }
        
        //난수 생성
        Random rand  = new Random();
		String numStr = "";
	    for(int i=0; i<6; i++) {
	        String ran = Integer.toString(rand.nextInt(10));
	        numStr+=ran;
	    }
	    
	    String smsContent = "[Mixin] 인증번호 ["+(numStr+ "]를 입력해 주세요." ); 
        
        // 위에 사항이 아니면 DB에 저장
        VerificationCode.setPhoneNumber(phoneNumber);
        VerificationCode.setCode(numStr);
        VerificationCode.setRequestTime(now);
        VerificationCode.setRequestCount(VerificationCode.getRequestCount() + 1);
        
        smsVerificationCodeRepository.save(VerificationCode);
        
        MessageDTO messageDto= new MessageDTO();
        messageDto.setContext(smsContent);
        messageDto.setTo(phoneNumber);
        
        // 메세지 전송
        SmsResponseDTO response= smsService.sendSms(messageDto);
        
        return response;
    }
    

    public boolean verifyCode(String phoneNumber, String code) {
        Optional<SmsVerification> verificationCode = smsVerificationCodeRepository.findById(phoneNumber);
        if (verificationCode.isPresent() && verificationCode.get().getCode().equals(code)) {
        	smsVerificationCodeRepository.delete(verificationCode.get());
            return true;
        }
        return false;
    }
}
