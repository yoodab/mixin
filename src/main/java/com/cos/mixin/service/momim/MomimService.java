package com.cos.mixin.service.momim;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.mixin.config.auth.LoginUser;
import com.cos.mixin.domain.momim.Momim;
import com.cos.mixin.domain.momim.MomimRepository;
import com.cos.mixin.domain.momim.joining.Joining;
import com.cos.mixin.domain.momim.joining.JoiningRepository;
import com.cos.mixin.dto.Momim.MomimReqDto.CreateMomimReqDto;
import com.cos.mixin.dto.Momim.MomimReqDto.JoinMomimReqDto;
import com.cos.mixin.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MomimService {

	private final MomimRepository momimRepository;
	private final JoiningRepository joiningRepository;

	public List<Momim> 내모임정보(LoginUser loginUser) {
		List<Joining> myMomimList = joiningRepository.findByUserId(loginUser.getUser());
		System.out.println(myMomimList);
		return null;
	}

	public void 코드모임검색() {
		
	}
	
	public void 전체모임정보() {

	}

	public void 카테고리모임정보() {

	}

	@Transactional
	public void 모임가입(JoinMomimReqDto joinMomimReqDto, LoginUser loginUser) {
		try {
			joinMomimReqDto.setStatus("대기");
			joiningRepository.mMomimJoining(joinMomimReqDto.getMomimId(), loginUser.getUser().getId(),
					joinMomimReqDto.getReson(), joinMomimReqDto.getStatus());
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}

	}

	@Value("${file.momimpath}")
	private String uploadFolder;

	@Transactional
	public String 모임생성(CreateMomimReqDto createMomimReqDto, LoginUser loginUser) {
		UUID uuid = UUID.randomUUID();

		String imageFileName = null;
		if (createMomimReqDto.getMomimThumbnailFile() != null) {
			imageFileName = uuid + "_" + createMomimReqDto.getMomimThumbnailFile().getOriginalFilename();

			Path imageFilePath = Paths.get(uploadFolder + imageFileName);

			try {
				Files.write(imageFilePath, createMomimReqDto.getMomimThumbnailFile().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Momim momimEntity = createMomimReqDto.toEntity(imageFileName, loginUser.getUser());
		// 모임에 코드 부여
		Random rand = new Random();
		String code = "";
		for (int i = 0; i < 6; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			code += ran;
		}
		momimEntity.setMomimCode(code);

		// 카테고리 제외 저장
		momimEntity = momimRepository.save(momimEntity);

		// 카테고리 설정
		momimRepository.mSetCategoryMomim(createMomimReqDto.getMomimName(), createMomimReqDto.getMomimCategory(),
				loginUser.getUser().getId());
		joiningRepository.mMomimJoining(momimEntity.getId(), loginUser.getUser().getId(), null, "승인");
		return "모임생성 성공";
	}

	@Transactional
	public void 모임삭제(Long momimId, LoginUser loginUser) {
		Momim momimEntity = momimRepository.findById(momimId).get();
		if (loginUser.getUser().getId() == momimEntity.getMomimLeader().getId()) {
			momimRepository.delete(momimEntity);
		} else {
			throw new CustomApiException("모임 삭제 권한이 없습니다.");
		}
	}
}
