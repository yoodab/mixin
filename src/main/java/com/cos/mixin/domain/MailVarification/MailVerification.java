package com.cos.mixin.domain.MailVarification;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.cos.mixin.domain.smsVerification.SmsVerification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class MailVerification {
	@Id
    private String userEmail;
    private String code;
    private LocalDateTime requestTime;
    private int requestCount;
    
    
    @Builder
	public MailVerification(String userEmail, String code, LocalDateTime requestTime, int requestCount) {
		this.userEmail = userEmail;
		this.code = code;
		this.requestTime = requestTime;
		this.requestCount = requestCount;
	}
}
