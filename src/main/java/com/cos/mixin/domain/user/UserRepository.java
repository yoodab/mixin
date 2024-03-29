package com.cos.mixin.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserEmail(String userEmail);
	
	Optional<User> findByUserPhoneNumber(String userPhoneNumber);
}
