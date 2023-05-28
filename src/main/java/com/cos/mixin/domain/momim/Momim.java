package com.cos.mixin.domain.momim;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cos.mixin.domain.category.Category;
import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor // 스프링이 User 객체생성할 때 빈생성자로 new를 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Momim {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 코드
	private String momimCode;
	// 이름
	private String momimName;
	// 타입
	private String momimType;
	
	// 카테고리
	@ManyToOne
    @JoinColumn(name = "categoryId")
	private Category momimCategory;
	
	@ManyToOne
    @JoinColumn(name = "momimLeaderId")
	private User momimLeader;
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
	
	@CreatedDate // Insert
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate // Insert, Update
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	
	@Builder
	public Momim(Long id, String momimCode, String momimName, String momimType, String momimTags,
			String momimDescription, int momimMemberLimit, String genderRestriction, String momoimAdmissionCriteria,
			String momoimRequirements, String momoimThumbnail, String momoimRecruitmentPeriod, String momoimFrequency,
			String momoimRules, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.momimCode = momimCode;
		this.momimName = momimName;
		this.momimType = momimType;
		this.momimTags = momimTags;
		this.momimDescription = momimDescription;
		this.momimMemberLimit = momimMemberLimit;
		this.genderRestriction = genderRestriction;
		this.momoimAdmissionCriteria = momoimAdmissionCriteria;
		this.momoimRequirements = momoimRequirements;
		this.momoimThumbnail = momoimThumbnail;
		this.momoimRecruitmentPeriod = momoimRecruitmentPeriod;
		this.momoimFrequency = momoimFrequency;
		this.momoimRules = momoimRules;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
}
