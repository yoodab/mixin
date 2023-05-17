package com.cos.mixin.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.mixin.domain.school.Department;
import com.cos.mixin.domain.school.DepartmentRepository;
import com.cos.mixin.domain.school.School;
import com.cos.mixin.domain.school.SchoolDepartment;
import com.cos.mixin.domain.school.SchoolDepartmentRepository;
import com.cos.mixin.domain.school.SchoolRepository;
import com.cos.mixin.domain.school.data.Content;
import com.cos.mixin.domain.school.data.DataSearch;
import com.cos.mixin.domain.verification.sms.SmsVerification;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SchoolService {

	private final SchoolRepository schoolRepository;
	private final DepartmentRepository departmentRepository;
	private final SchoolDepartmentRepository schoolDepartmentRepository;

	public List<School> 학교명() {
		List<School> schoolName = schoolRepository.findAll();
		return schoolName;
	}

	public List<Department> 학과명(String schoolName) {

		School school = schoolRepository.findBySchoolName(schoolName);
		if (school != null) {
			return schoolDepartmentRepository.findDepartmentsBySchoolId(school.getId());
		}
		return Collections.emptyList();
	}

	public void DepartmentData(String schoolName, String departmentName) {
		// 학교명 입력받아서 학교명 찾아서 객체 만들기
		School school = schoolRepository.findBySchoolName(schoolName);
		if (school == null) {
			school.setSchoolName(schoolName);
			schoolRepository.save(school);
		}
		// 학과명 입력받아서 학과명 있으면 없으면 새로 만들어서 저장하고 아이디
		Department department = departmentRepository.findByName(departmentName);
		if (department == null) {
			department = new Department();
			department.setName(departmentName);
			System.out.println(departmentName);
			departmentRepository.save(department);
		}
		
		SchoolDepartment schoolDepartment = new SchoolDepartment();
		schoolDepartment.setSchool(school);
		schoolDepartment.setDepartment(department);

		schoolDepartmentRepository.save(schoolDepartment);

	}

	public void saveSchoolDataFromFile() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Long i = (long) 0;
			InputStream inputStream = getClass().getResourceAsStream("/school_data.json");
			DataSearch dataSearch = objectMapper.readValue(inputStream, DataSearch.class);
			List<Content> contentList = dataSearch.getContent();
			schoolRepository.deleteAll();

			for (Content content : contentList) {
				School school = new School();

				school.setId(i++);
				school.setSchoolName(content.getSchoolName());

				schoolRepository.save(school);
			}

			System.out.println("Data saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
