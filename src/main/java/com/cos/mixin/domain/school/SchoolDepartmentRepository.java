package com.cos.mixin.domain.school;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SchoolDepartmentRepository extends JpaRepository<SchoolDepartment, Long> {

	@Query("SELECT sd.department FROM SchoolDepartment sd WHERE sd.school.id = :schoolId")
    List<Department> findDepartmentsBySchoolId(Long schoolId);
}
