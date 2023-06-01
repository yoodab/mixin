package com.cos.mixin.domain.momim.joining;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.mixin.domain.user.User;

public interface JoiningRepository extends JpaRepository<Joining, Long>{
	
	List<Joining> findByUserId(User user);
	
	@Modifying 
	@Query(value="INSERT INTO joining(momimId, userId, reason, status,createdAt) VALUES(:momimId,:userId,:reason,:status,now())",nativeQuery=true)
	void mMomimJoining(Long momimId, Long userId, String reason, String status); 
}
