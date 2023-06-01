package com.cos.mixin.service.school;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cos.mixin.domain.school.School;
import com.cos.mixin.domain.school.SchoolRepository;
import com.cos.mixin.domain.school.data.Content;
import com.cos.mixin.domain.school.data.DataSearch;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SchoolService {

	private final SchoolRepository schoolRepository;

	public List<School> 학교명() {
		List<School> schoolName = schoolRepository.findByAllSchoolName();
		return schoolName;
	}

	public School 학과명(String schoolName) {

		School school = schoolRepository.findBySchoolName(schoolName);
		System.out.println(school);
		return school;
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
				school.setAddress(content.getAdres());

				schoolRepository.save(school);
			}

			System.out.println("Data saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
