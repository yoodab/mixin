package com.cos.mixin.domain.interest;

import javax.persistence.Entity;

import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Interest {
	@Id
	private int id;
	
	private String interests;
	
}
