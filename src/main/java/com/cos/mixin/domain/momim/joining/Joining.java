package com.cos.mixin.domain.momim.joining;


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
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cos.mixin.domain.momim.Momim;
import com.cos.mixin.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor // 스프링이 User 객체생성할 때 빈생성자로 new를 하기 때문!!
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table( // 제약조건 복합적으로 만들때
		uniqueConstraints={
			@UniqueConstraint(
				name = "Joining_uk",
				columnNames={"momimId","userId"}
			)
		}
	)
public class Joining {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "momimId") //DB 컬럼명 지정
	@ManyToOne
	private Momim momimId;
	
	@JoinColumn(name = "userId")
	@ManyToOne
	private User userId;
	
	private String status;
	
	private String reason;
	
	@CreatedDate // Insert
	@Column(nullable = false)
	private LocalDateTime createdAt;

}
