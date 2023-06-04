package com.cos.mixin.domain.school;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.mixin.dto.school.SchoolDto;


public interface SchoolRepository extends JpaRepository<School, Long>{
	 
	 
	 @Query(value = "SELECT * FROM school WHERE schoolName = :schoolName", nativeQuery = true)
	 School findBySchoolName(String schoolName);
	 
	 @Query("SELECT new com.cos.mixin.dto.school.SchoolDto(s.schoolName, s.address) FROM School s")
	    List<SchoolDto> findAllSchoolNamesAndAddresses();
	 
}
	
