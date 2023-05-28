package com.cos.mixin.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cos.mixin.domain.momim.Momim;
import com.cos.mixin.domain.momim.MomimRepository;
import com.cos.mixin.domain.momim.joining.JoiningRepository;
import com.cos.mixin.dto.Momim.MomimReqDto.CreateMomimReqDto;
import com.cos.mixin.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MomimService {

	private final MomimRepository momimRepository;
	private final JoiningRepository joiningRepository;

	public List<Momim> 모임정보() {
		List<Momim> momimEntity = momimRepository.findAll();
		return momimEntity;
	}
	
	public void 모임가입(int momimId, int userId) {
		try {
			joiningRepository.mMomimJoining(momimId, userId);
		} catch (Exception e) {
			throw new CustomApiException("이미 가입된 모임입니다.");
		}
		
	}

	public String 모임생성(CreateMomimReqDto createMomimReqDto,Long momimLeaderId) {
		Momim momimEntity = createMomimReqDto.toEntity();
		// 모임에 코드 부여
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			code += ran;
		}
		momimEntity.setMomimCode(code);
		
		// 카테고리 제외 저장
		momimRepository.save(momimEntity);
		
		// 카테고리 설정
		momimRepository.mSetCategoryMomim(createMomimReqDto.getMomimName(),createMomimReqDto.getMomimCategory(),momimLeaderId);

		return "모임생성 성공";
	}
}
