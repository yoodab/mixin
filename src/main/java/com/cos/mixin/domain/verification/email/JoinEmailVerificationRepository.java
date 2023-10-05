package com.cos.mixin.domain.verification.email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JoinEmailVerificationRepository extends JpaRepository<JoinEmailVerification, Long>{
	Optional<JoinEmailVerification> findByUserEmail(String userEmail);
}
