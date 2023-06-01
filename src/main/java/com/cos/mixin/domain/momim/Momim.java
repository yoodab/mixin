package com.cos.mixin.domain.momim;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cos.mixin.domain.category.Category;
import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Data
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
	private String momimAdmissionCriteria;
	// 가입조건
	private String momimRequirements;
	// 썸네일
	private String momimThumbnailUrl;
	// 모집기간
	private String momimRecruitmentPeriod;
	// 모임주기
	private String momimFrequency;
	// 모임규칙
	private String momimRules;
	
	
	@CreatedDate // Insert
	@Column(nullable = false)
	private LocalDateTime createdAt;

	
	
	
	
}
