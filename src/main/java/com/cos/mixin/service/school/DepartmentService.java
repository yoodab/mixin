package com.cos.mixin.service.school;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.cos.mixin.domain.school.School;
import com.cos.mixin.domain.school.SchoolRepository;
import com.cos.mixin.domain.school.collegeDepartment.College;
import com.cos.mixin.domain.school.collegeDepartment.CollegeRepository;
import com.cos.mixin.domain.school.collegeDepartment.Departments;
import com.cos.mixin.domain.school.collegeDepartment.DepartmentsRepository;
import com.cos.mixin.domain.school.data.CollegeData;
import com.cos.mixin.domain.school.data.DepartmentData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DepartmentService {

	final private CollegeRepository collegeRepository;
	final private DepartmentsRepository departmentsRepository;
	private final SchoolRepository schoolRepository;
	
	 public void populateDataFromJson() {
	        try {
	            ClassPathResource resource = new ClassPathResource("department.json");
	            byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
	            String jsonString = new String(jsonData, StandardCharsets.UTF_8);

	            ObjectMapper objectMapper = new ObjectMapper();
	            List<DepartmentData> departmentDataList = objectMapper.readValue(jsonString, new TypeReference<List<DepartmentData>>() {});

	            Set<String> collegesSet = new HashSet<>();
	            Set<String> departmentsSet = new HashSet<>();

	            for (DepartmentData departmentData : departmentDataList) {
	                String schoolName = departmentData.getSchoolName();
	                School school = schoolRepository.findBySchoolName(schoolName);

	                if (school == null) {
	                    school = new School();
	                    school.setSchoolName(schoolName);
	                    schoolRepository.save(school);
	                }

	                List<CollegeData> colleges = departmentData.getColleges();

	                for (CollegeData collegeData : colleges) {
	                    String collegeName = collegeData.getCollegeName();
	                    List<String> departments = collegeData.getDepartments();

	                    collegesSet.add(collegeName);
	                    departmentsSet.addAll(departments);

	                    College college = new College();
	                    college.setCollegeName(collegeName);
	                    college.setSchool(school);
	                    collegeRepository.save(college);

	                    for (String departmentName : departments) {
	                        Departments department = new Departments();
	                        department.setCollege(college);
	                        department.setDepartmentName(departmentName);

	                        departmentsRepository.save(department);
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}
