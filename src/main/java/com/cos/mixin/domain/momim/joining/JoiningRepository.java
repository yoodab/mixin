package com.cos.mixin.domain.momim.joining;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JoiningRepository extends JpaRepository<Joining, Long>{

	
	@Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성시 해당 어노테이션 필요함
	@Query(value="INSERT INTO joining(momimId, userId, createDate) VALUES(:momimId,:userId,now())",nativeQuery=true)
	// 네이티브 쿼리 작성시 nativeQuery=true 옵션 들어가야함
	void mMomimJoining(int momimId, int userId); // void말고 int시 성공 = 변경된 행의 개수 리턴 , 실패 = -1 리턴
}
