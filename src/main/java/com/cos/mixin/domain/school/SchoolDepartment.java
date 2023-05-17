package com.cos.mixin.domain.school;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						columnNames = {"schoolId", "departmentId"}
						)
				}
		)
public class SchoolDepartment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schoolId")
    private School school;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;
}
