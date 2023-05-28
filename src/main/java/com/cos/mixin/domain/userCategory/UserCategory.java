package com.cos.mixin.domain.userCategory;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cos.mixin.domain.category.Category;
import com.cos.mixin.domain.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor 
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class UserCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "categoryId") 
	@ManyToOne
	private Category category;
	
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user;
	
}
