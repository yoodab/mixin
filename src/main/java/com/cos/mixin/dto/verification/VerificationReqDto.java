package com.cos.mixin.dto.verification;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class VerificationReqDto {
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class SmsDto {
		// sms
		private String userName;
		private String to;		
	    private String content;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class SmsCheckDto {
		// 번호
		private String userPhoneNumber;		
		// 인증번호 
		private String code;	
	}
	

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class SmsReqDto {
		String type;
		String contentType;
		String countryCode;
		String from;
		String content;
		List<SmsDto> messages;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class EmailDto {
		// email
		private String userEmail; 

	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class EmailCheckDto {
		// email
		private String userEmail;
		// 인증번호 
		private String code;
	}
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class MailReqDto {
		private String userEmail;
		private String title;
		private String content;
		// 인증번호
		private String code;
	}
}
