package com.cos.mixin.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	Optional<UserProfile> findByUser(User user);
}
