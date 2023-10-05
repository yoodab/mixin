package com.cos.mixin.dto.verification;

import java.time.LocalDateTime;
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
	public static class FindPasswordDto {
		private String emailOrPhoneNum;
		
	}
	
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
		
		private String verificationType;
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
	public static class SmsVeriDto{
		private Long id;
	    private String userPhoneNumber;
	    private String code;
	    private String userName;
	    private LocalDateTime requestTime;
	    private int requestCount;
	    private String verificationType;
	}
	

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class EmailDto {
		// email
		private String userEmail;
		private String verificationType;

	}
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class EmailReqDto {
		
		private Long id;
	    private String userEmail;
	    private String code;
	    private LocalDateTime requestTime;
	    private int requestCount;

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
		
		private String verificationType;
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
