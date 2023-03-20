package com.cos.mixin.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// 아이디
	@Column(length = 20, unique = true)
	private String userEmail;

	// 비번
	@Column(nullable = false)
	private String userPassword;

	// 이름
	@Column(nullable = false)
	private String userName;

	// 성별
	private String userSex;
	
	// 번호
	private String userPhoneNumber;

	// 학교명
	private String userUniversity;

	// 학번
	private String userStudnetId;

	// 학과
	private String userDepartment;

	// 권한
	@ColumnDefault("'ROLE_USER'")
	private String role;

	// 가입시간
	private LocalDateTime createDate;

	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
