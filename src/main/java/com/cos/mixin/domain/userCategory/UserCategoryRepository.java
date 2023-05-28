package com.cos.mixin.domain.userCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserCategoryRepository extends JpaRepository<UserCategory, Long>{
	
	List<UserCategory> findByUserId(Long userId);
	
	@Modifying 
	@Query(value = "INSERT INTO usercategory (userId, categoryId) VALUES (:userId, (SELECT id FROM category WHERE category = :categoryName))", nativeQuery = true)
	void mSetUserCategory(Long userId, String categoryName); 
	
	@Modifying 
	@Query(value = "DELETE FROM usercategory WHERE userId = :userId AND categoryId = (SELECT id FROM category WHERE category = :categoryName)", nativeQuery = true)
	void mDelUserCategory(Long userId, String categoryName);
}
