package com.cos.mixin.domain.school;


import org.springframework.data.jpa.repository.JpaRepository;


public interface SchoolRepository extends JpaRepository<School, Long>{
	 School findBySchoolName(String name);
}
	
