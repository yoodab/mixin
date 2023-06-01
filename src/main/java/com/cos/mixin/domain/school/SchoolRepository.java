package com.cos.mixin.domain.school;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SchoolRepository extends JpaRepository<School, Long>{
	 
	 @Query(value = "SELECT id, address, schoolName FROM school", nativeQuery = true)
	 List<School> findByAllSchoolName();
	 
	 @Query(value = "SELECT * FROM school WHERE schoolName = :schoolName", nativeQuery = true)
	 School findBySchoolName(String schoolName);
}
	
