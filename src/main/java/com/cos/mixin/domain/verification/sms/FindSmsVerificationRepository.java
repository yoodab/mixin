package com.cos.mixin.domain.verification.sms;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface FindSmsVerificationRepository extends JpaRepository<FindSmsVerification, Long> {
	
	Optional<FindSmsVerification> findByPhoneNumber(String phoneNumber);
}

