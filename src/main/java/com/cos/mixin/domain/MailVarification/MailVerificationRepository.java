package com.cos.mixin.domain.MailVarification;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MailVerificationRepository extends JpaRepository<MailVerification, String>{
	Optional<MailVerification> findByUserEmail(String userEmail);
}
