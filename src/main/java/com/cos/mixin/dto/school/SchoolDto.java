package com.cos.mixin.dto.school;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolDto {
	private String schoolName;
    private String address;
}
