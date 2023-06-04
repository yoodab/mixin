package com.cos.mixin.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnoreProperties({"userProfile"})
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", unique = true)
	private User user;
	
	// 역할
	private String position;
	// 성격
	private String personality;
	// 가치관
	private String userValues;
	// 별명
	private String nickname;
	// 프로필사진
	private String profilePictureUrl;
	// 자기소개
	private String introduction;
	
	private int userSmileDegree;
	
	@CreatedDate // Insert
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate // Insert, Update
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Builder
	public UserProfile(Long id, User user, String position, String personality, String userValues, String nickname,
			String profilePictureUrl, String introduction, int userSmileDegree, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.user = user;
		this.position = position;
		this.personality = personality;
		this.userValues = userValues;
		this.nickname = nickname;
		this.profilePictureUrl = profilePictureUrl;
		this.introduction = introduction;
		this.userSmileDegree = userSmileDegree;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
}
