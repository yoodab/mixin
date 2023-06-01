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

import com.cos.mixin.domain.verification.sms.SmsVerification;
import com.cos.mixin.domain.verification.sms.SmsVerificationRepository;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsCheckDto;
import com.cos.mixin.dto.verification.VerificationReqDto.SmsDto;
import com.cos.mixin.dto.verification.VerificationRespDto.MessageRespDto;
import com.cos.mixin.dto.verification.VerificationVO;
import com.cos.mixin.handler.ex.CustomApiException;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SmsVerificationService {
    @Autowired
    private SmsVerificationRepository smsVerificationCodeRepository;
    
    @Autowired
    private SmsService smsService;


    
    // 번호로 처음 인증번호 눌렀을때
    public MessageRespDto sendVerificationCode(String phoneNumber) throws RestClientException, InvalidKeyException, JsonProcessingException, NoSuchAlgorithmException, UnsupportedEncodingException, URISyntaxException {
        LocalDateTime now = LocalDateTime.now();
        
        //난수 생성
        Random rand  = new Random();
		String code = "";
	    for(int i=0; i<6; i++) {
	        String ran = Integer.toString(rand.nextInt(10));
	        code+=ran;
	    }
        
        
        SmsVerification VerificationCode = smsVerificationCodeRepository.findByPhoneNumber(phoneNumber).orElse(new SmsVerification());
        // 인증 횟수 확인	
        if (VerificationCode.getRequestCount() >= 2) {
            // 인증실패 5회하고 다음 시도는 1일 지나고 가능
            if (VerificationCode.getRequestTime() != null	&& VerificationCode.getRequestTime().plusMinutes(1).isAfter(now)) 
            	throw new CustomApiException("인증 횟수 초과");                                                                     
        }else {
        	smsVerificationCodeRepository.delete(VerificationCode);
        	VerificationCode.setRequestCount(0);
        }
     
        
       
        
        // 위의 경우가 아니면 DB에 저장
        VerificationCode.setPhoneNumber(phoneNumber);
        VerificationCode.setCode(code);
        VerificationCode.setRequestTime(now);
        VerificationCode.setRequestCount(VerificationCode.getRequestCount() + 1);
        
        smsVerificationCodeRepository.save(VerificationCode);
        
        
        String smsContent = VerificationVO.SMS_PREFIX+code+ VerificationVO.SMS_POSTFIX ; 
        
        SmsDto smsDto= new SmsDto();
        smsDto.setContent(smsContent);
        smsDto.setTo(phoneNumber);
        
        // 메세지 전송
        MessageRespDto response = smsService.sendSms(smsDto);

        
        return response;
    }
    

    public boolean verifyCode(SmsCheckDto smsCheckDto) {
		LocalDateTime now = LocalDateTime.now(); 
        Optional<SmsVerification> VerificationCode = smsVerificationCodeRepository.findById(smsCheckDto.getUserPhoneNumber());
        if (VerificationCode.isPresent() && VerificationCode.get().getRequestTime().plusMinutes(3).isAfter(now)) {
        	if (VerificationCode.isPresent() && VerificationCode.get().getCode().equals(smsCheckDto.getCode())) {
            	smsVerificationCodeRepository.delete(VerificationCode.get());
                return true;
            }
        }
        
        return false;
    }
}
