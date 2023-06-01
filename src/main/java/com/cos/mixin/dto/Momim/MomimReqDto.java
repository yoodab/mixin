package com.cos.mixin.dto.Momim;

import org.springframework.web.multipart.MultipartFile;

import com.cos.mixin.domain.momim.Momim;
import com.cos.mixin.domain.user.User;

import lombok.Data;

public class MomimReqDto {
	@Data
	public static class JoinMomimReqDto{
		// 모임아이디
		private Long momimId;
		
		// 지원이유
		private String reson;
		
		// 상태 
		private String status;
		
	}
	
	

	@Data
	public static class CreateMomimReqDto{
		// 이름
		private String momimName;
		// 타입
		private String momimType;
		// 카테고리
		private String momimCategory;
		// 테그
		private String momimTags;
		// 소개
		private String momimDescription;
		// 인원수
		private int momimMemberLimit;
		// 성별
		private String genderRestriction;
		// 가입기준
		private String momimAdmissionCriteria;
		// 가입조건
		private String momimRequirements;
		// 썸네일
		private MultipartFile momimThumbnailFile;
		// 모집기간
		private String momimRecruitmentPeriod;
		// 모임주기
		private String momimFrequency;
		// 모임규칙
		private String momimRules;
		
		public Momim toEntity(String momimThumbnailUrl, User user) {
			return Momim.builder()
					.momimLeader(user)
					.momimName(momimName)
					.momimType(momimType)
					.momimTags(momimTags)
					.momimDescription(momimDescription)
					.genderRestriction(genderRestriction)
					.momimAdmissionCriteria(momimAdmissionCriteria)
					.momimRequirements(momimRequirements)
					.momimThumbnailUrl(momimThumbnailUrl)
					.momimRecruitmentPeriod(momimRecruitmentPeriod)
					.momimFrequency(momimFrequency)
					.momimRules(momimRules)
					.build();
		}
	}
}
