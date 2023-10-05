package com.cos.mixin.domain.verification.email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FindEmailVerificationRepository extends JpaRepository<FindEmailVerification, Long>{
	Optional<FindEmailVerification> findByUserEmail(String userEmail);
}
