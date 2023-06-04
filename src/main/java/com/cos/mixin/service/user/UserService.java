package com.cos.mixin.service.user;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.cos.mixin.domain.category.Category;
import com.cos.mixin.domain.category.CategoryRepository;
import com.cos.mixin.domain.school.School;
import com.cos.mixin.domain.school.collegeDepartment.College;
import com.cos.mixin.domain.school.collegeDepartment.Departments;
import com.cos.mixin.domain.school.data.CollegeData;
import com.cos.mixin.domain.school.data.DepartmentData;
import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserEnum;
import com.cos.mixin.domain.user.UserRepository;
import com.cos.mixin.domain.userCategory.UserCategory;
import com.cos.mixin.domain.userCategory.UserCategoryRepository;
import com.cos.mixin.dto.user.UserReqDto.JoinReqDto;
import com.cos.mixin.dto.user.UserRespDto.JoinRespDto;
import com.cos.mixin.handler.ex.CustomApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final UserRepository userRepository;
	private final UserCategoryRepository userCategoryRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CategoryRepository categoryRepository;

	// 서비스는 DTO를 요청받고, DTO로 응답한다.
	@Transactional // 트랜잭션이 메서드 시작할 때, 시작되고, 종료될 때 함께 종료
	public JoinRespDto 회원가입(JoinReqDto joinReqDto) {

		// 1. 동일 유저네임 존재 검사
		Optional<User> userOP = userRepository.findByUserEmail(joinReqDto.getUserEmail());
		if (userOP.isPresent()) {
			// 유저네임 중복되었다는 뜻
			throw new CustomApiException("동일한 username이 존재합니다");
		}

		// 2. 회원가입 Role설정
		User userEntity = joinReqDto.toEntity(passwordEncoder);
		userEntity.setRoles(UserEnum.CUSTOMER);

		// 3. 패스워드 인코딩 + 회원가입
		User userPS = userRepository.save(userEntity);

		// 4. dto 응답
		return new JoinRespDto(userPS);
	}

	@Transactional
	public void 유저카테고리등록(long id, List<String> categorys) {
		// 입력받은 데이터 List를 String 배열로 변환
		String[] categoryNames = categorys.toArray(new String[0]);

		// DB에 저장되어 있는 카테고리 리스트 받기
		List<UserCategory> categoryList = userCategoryRepository.findByUserId(id);

		// Remove categories that are not present in the input
		for (UserCategory existingCategory : categoryList) {
			if (!Arrays.asList(categoryNames).contains(existingCategory.getCategory().getCategoryName())) {
				userCategoryRepository.mDelUserCategory(id, existingCategory.getCategory().getCategoryName());
			}
		}

		// 원래 있던 리스트에서 입력 안된 부분 제거
		categoryList.removeIf(category -> !Arrays.asList(categoryNames).contains(category.getCategory().getCategoryName()));

		for (String categoryName : categoryNames) {
			if (categoryList.stream()
					.noneMatch(category -> category.getCategory().getCategoryName().equals(categoryName))) {
				userCategoryRepository.mSetUserCategory(id, categoryName);
			}
		}

	}

	public void 카테고리등록() {
		try {
			ClassPathResource resource = new ClassPathResource("category.json");
			byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
			String jsonString = new String(jsonData, StandardCharsets.UTF_8);

			ObjectMapper objectMapper = new ObjectMapper();
			List<Category> categoryDataList = objectMapper.readValue(jsonString, new TypeReference<List<Category>>() {
			});


			for (Category categoryData : categoryDataList) {
				int categoryId = categoryData.getId();
				String category = categoryData.getCategoryName();

				Category categoryEntity = new Category();
				categoryEntity.setId(categoryId);
				categoryEntity.setCategoryName(category);

				categoryRepository.save(categoryEntity);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User 유저정보보기(long id) {
		User userEntity = userRepository.findById(id).get();
		return userEntity;
	}
}
