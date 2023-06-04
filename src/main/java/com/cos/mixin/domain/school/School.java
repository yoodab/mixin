package com.cos.mixin.domain.school;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cos.mixin.domain.school.collegeDepartment.College;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@Column(nullable = false)
	private String address;
	
	@JsonIgnoreProperties({"school"})
	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<College> colleges;

	
	
}
