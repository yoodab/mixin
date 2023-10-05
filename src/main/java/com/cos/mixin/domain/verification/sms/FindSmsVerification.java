package com.cos.mixin.domain.verification.sms;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cos.mixin.dto.verification.VerificationReqDto.SmsVeriDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class FindSmsVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String phoneNumber;
    private String code;
    private LocalDateTime requestTime;
    private int requestCount;
    
    
    
    
    @Builder
	public FindSmsVerification(String phoneNumber, String code, LocalDateTime requestTime, int requestCount) {
		this.phoneNumber = phoneNumber;
		this.code = code;
		this.requestTime = requestTime;
		this.requestCount = requestCount;
	}

    @Builder
	public FindSmsVerification(SmsVeriDto smsVeriDto) {
    	this.id=smsVeriDto.getId();
		this.phoneNumber = smsVeriDto.getUserPhoneNumber();
		this.code = smsVeriDto.getCode();
		this.requestTime = smsVeriDto.getRequestTime();
		this.requestCount = smsVeriDto.getRequestCount();
	}
    
    
    // 생성자, 게터/세터, toString 등 필요한 메서드 작성
}
