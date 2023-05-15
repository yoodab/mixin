package com.cos.mixin.dto.school;

import lombok.Getter;
import lombok.Setter;

public class SchoolDto {

	@Setter
    @Getter
    public static class SchoolNameDto {
        private String SchoolName;
    }
	
	
	@Setter
    @Getter
	public static class SchoolDepartmentDto{
		private String Department;
	}
}
