package com.cos.mixin.dto.verification;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class VerificationRespDto {

	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class MessageRespDto {
		String requestId;
		LocalDateTime requestTime;
		String statusCode;
		String statusName;
		String content;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@Builder
	public static class EmailRespDto {
		LocalDateTime requestTime;
		int RequestCount;
		String statusName;
	}
	
}
