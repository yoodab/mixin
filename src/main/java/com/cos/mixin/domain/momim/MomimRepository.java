package com.cos.mixin.domain.momim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MomimRepository extends JpaRepository<Momim, Long>{
	@Modifying // INSERT, DELETE, UPDATE 를 네이티브 쿼리로 작성시 해당 어노테이션 필요함"UPDATE momim SET category_id = ? WHERE momim_name = ?"
    //@Query(value = "UPDATE momim SET categoryId = (SELECT id FROM category WHERE name = :categoryName) WHERE momimName = :momimName", nativeQuery = true)
	// 네이티브 쿼리 작성시 nativeQuery=true 옵션 들어가야함
	@Query(value = "UPDATE momim SET categoryId = (SELECT id FROM category WHERE category = :categoryName), momimLeaderId = :momimLeaderId WHERE momimName = :momimName", nativeQuery = true)
	void mSetCategoryMomim(String momimName, String categoryName,Long momimLeaderId); // void말고 int시 성공 = 변경된 행의 개수 리턴 , 실패 = -1 리턴
}
