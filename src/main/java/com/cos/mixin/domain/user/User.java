package com.cos.mixin.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor // 스프링이 User 객체생성할 때 빈생성자로 new를 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_tb")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 아이디
	@Column(length = 40, unique = true, nullable = false)
	private String userEmail;

	private String agradInfrm;

	// 비번
	@Column(nullable = false)
	private String userPassword;

	// 이름
	@Column(nullable = false)
	private String userName;

	// 성별
	private String userGender;

	// 번호
	private String userPhoneNumber;

	private String userCarrier;

	// 학교명
	private String userUniversity;

	// 학번
	private String userStudentId;

	// 학과
	private String userDepartment;

	// 권한
	@Enumerated(EnumType.STRING)
	@ColumnDefault("'ROLE_USER'")
	private UserEnum roles;

	@CreatedDate // Insert
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate // Insert, Update
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	
	
	
	@Builder
    public User(Long id, String userEmail, String agradInfrm, String userPassword, String userName, String userGender,
			String userPhoneNumber, String userCarrier, String userUniversity, String userStudnetId,
			String userDepartment, UserEnum roles, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.userEmail = userEmail;
		this.agradInfrm = agradInfrm;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userGender = userGender;
		this.userPhoneNumber = userPhoneNumber;
		this.userCarrier = userCarrier;
		this.userUniversity = userUniversity;
		this.userStudentId = userStudnetId;
		this.userDepartment = userDepartment;
		this.roles = roles;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}







}
