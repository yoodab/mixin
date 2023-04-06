package com.cos.mixin.domain.verification.email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String>{
	Optional<EmailVerification> findByUserEmail(String userEmail);
}
