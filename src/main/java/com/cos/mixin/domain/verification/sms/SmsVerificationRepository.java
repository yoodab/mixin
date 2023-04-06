package com.cos.mixin.domain.verification.sms;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface SmsVerificationRepository extends JpaRepository<SmsVerification, String> {
	
	Optional<SmsVerification> findByPhoneNumber(String phoneNumber);
}

