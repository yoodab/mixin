package com.cos.mixin.domain.verification.email;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cos.mixin.dto.verification.VerificationReqDto.EmailReqDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class JoinEmailVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String userEmail;
    private String code;
    private LocalDateTime requestTime;
    private int requestCount;
    
    @Builder
	public JoinEmailVerification(String userEmail, String code, LocalDateTime requestTime, int requestCount) {
		this.userEmail = userEmail;
		this.code = code;
		this.requestTime = requestTime;
		this.requestCount = requestCount;
	}
    
    @Builder
	public JoinEmailVerification(EmailReqDto emailReqDto) {
    	this.id = emailReqDto.getId();
		this.userEmail = emailReqDto.getUserEmail();
		this.code = emailReqDto.getCode();
		this.requestTime = emailReqDto.getRequestTime();
		this.requestCount = emailReqDto.getRequestCount();
	}
}
