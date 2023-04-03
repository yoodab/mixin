package com.cos.mixin.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.mixin.domain.user.User;
import com.cos.mixin.domain.user.UserRepository;




// http://localhost:8080/login 요청시 실행됨
@Service
public class LoginService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    // 시큐리티로 로그인이 될때, 시큐리티가 loadUserByUsername() 실행해서 username을 체크!!
    // 없으면 오류
    // 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User userPS = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new InternalAuthenticationServiceException("인증 실패")); // 나중에 테스트할 때 설명해드림.
        return new LoginUser(userPS);
    }
	
	
}
