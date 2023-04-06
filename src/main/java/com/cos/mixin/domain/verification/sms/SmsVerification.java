package com.cos.mixin.domain.verification.sms;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class SmsVerification {
    @Id
    private String phoneNumber;
    private String code;
    private LocalDateTime requestTime;
    private int requestCount;
    
    
    
    
    @Builder
	public SmsVerification(String phoneNumber, String code, LocalDateTime requestTime, int requestCount) {
		this.phoneNumber = phoneNumber;
		this.code = code;
		this.requestTime = requestTime;
		this.requestCount = requestCount;
	}

    
    
    // 생성자, 게터/세터, toString 등 필요한 메서드 작성
}
