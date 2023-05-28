package com.cos.mixin.dto.Momim;

import com.cos.mixin.domain.momim.Momim;

import lombok.Data;

public class MomimReqDto {
	@Data
	public static class JoinMomimReqDto{
		// 모임아이디
		private String momimId;
		// 지원이유
		private String reson;
		
		
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
		private String momoimAdmissionCriteria;
		// 가입조건
		private String momoimRequirements;
		// 썸네일
		private String momoimThumbnail;
		// 모집기간
		private String momoimRecruitmentPeriod;
		// 모임주기
		private String momoimFrequency;
		// 모임규칙
		private String momoimRules;
		
		public Momim toEntity() {
			return Momim.builder()
					.momimName(momimName)
					.momimType(momimType)
					.momimTags(momimTags)
					.momimDescription(momimDescription)
					.genderRestriction(genderRestriction)
					.momoimAdmissionCriteria(momoimAdmissionCriteria)
					.momoimRequirements(momoimRequirements)
					.momoimThumbnail(momoimThumbnail)
					.momoimRecruitmentPeriod(momoimRecruitmentPeriod)
					.momoimFrequency(momoimFrequency)
					.momoimRules(momoimRules)
					.build();
		}
	}
}
