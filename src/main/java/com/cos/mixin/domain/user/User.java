package com.cos.mixin.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
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
	@Column(length = 20, unique = true, nullable = false)
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
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserEnum role;

	@CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
	public User(Long id, String userEmail, String userPassword, String userName, String userSex, String userPhoneNumber,
			String userUniversity, String userStudnetId, String userDepartment, UserEnum role, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userSex = userSex;
		this.userPhoneNumber = userPhoneNumber;
		this.userUniversity = userUniversity;
		this.userStudnetId = userStudnetId;
		this.userDepartment = userDepartment;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
    
}
