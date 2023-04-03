package com.cos.mixin.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CMRespDto<T> {
	private int code; // 성공 1 ,실패 -1
	private String message;
	private T data;
}
