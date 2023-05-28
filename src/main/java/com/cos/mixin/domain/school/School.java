package com.cos.mixin.domain.school;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class School {
	
	@Id
	private Long id;
	
	@Column(nullable = false)
	private String schoolName;
	
	
}