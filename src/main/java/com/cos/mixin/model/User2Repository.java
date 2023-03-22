package com.cos.mixin.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface User2Repository extends JpaRepository<User2, Long>{

	public User2 findByUsername(String username);
}
