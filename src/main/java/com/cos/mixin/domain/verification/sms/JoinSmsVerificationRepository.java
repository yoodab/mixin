package com.cos.mixin.domain.verification.sms;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface JoinSmsVerificationRepository extends JpaRepository<JoinSmsVerification, Long> {
	
	Optional<JoinSmsVerification> findByPhoneNumber(String phoneNumber);
}

