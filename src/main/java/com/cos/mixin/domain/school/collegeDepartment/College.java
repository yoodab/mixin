package com.cos.mixin.domain.school.collegeDepartment;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.cos.mixin.domain.school.School;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class College {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    private String collegeName;
    
    @ManyToOne
    @JoinColumn(name = "schoolId")
    private School school;
    
    @JsonIgnoreProperties({"college"})
    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    private List<Departments> departments;
    
}
