package com.cos.mixin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.domain.school.Department;
import com.cos.mixin.domain.school.School;
import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.dto.school.SchoolDepartmentDto;
import com.cos.mixin.service.SchoolService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SchoolController {
	
	private final SchoolService schoolService;
	
	@GetMapping("/school")
	public ResponseEntity<?> schoolName() {
		List<School> schoolName = schoolService.학교명();
		return new ResponseEntity<>(new ResponseDto<>(1, "학교명 확인 성공", schoolName), HttpStatus.OK);
	}
	
	
	@GetMapping("/{schoolName}/Department")
	public ResponseEntity<?> schoolDepartment(@PathVariable String schoolName) {
		List<Department> department = schoolService.학과명(schoolName);
		return new ResponseEntity<>(new ResponseDto<>(1, "학과명 확인 성공", department), HttpStatus.OK);
	}

	@GetMapping("/school/update")
	public ResponseEntity<?> schoolData(){
	    schoolService.saveSchoolDataFromFile();
		return new ResponseEntity<>(new ResponseDto<>(1, "학교데이터 만들기", null), HttpStatus.OK);
	}
	
	@PostMapping("/school/department")
	public ResponseEntity<?> DepartmentDataSet(@RequestBody SchoolDepartmentDto schoolDepartmentDto){
	    schoolService.DepartmentData(schoolDepartmentDto.getSchoolName(), schoolDepartmentDto.getDepartmentName());
		return new ResponseEntity<>(new ResponseDto<>(1, "학과 만들기", null), HttpStatus.OK);
	}
	
}



    