package com.cos.mixin.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;
import com.cos.mixin.dto.user.UserReqDto.JoinReqDto;
import com.cos.mixin.dto.user.UserRespDto.JoinRespDto;
import com.cos.mixin.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 서비스는 DTO를 요청받고, DTO로 응답한다.
    @Transactional // 트랜잭션이 메서드 시작할 때, 시작되고, 종료될 때 함께 종료
    public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
    	
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUserEmail(joinReqDto.getUserEmail());
        if (userOP.isPresent()) {
            // 유저네임 중복되었다는 뜻
            throw new CustomApiException("동일한 username이 존재합니다");
        }

        // 2. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 3. dto 응답
        return new JoinRespDto(userPS);
    }
}
