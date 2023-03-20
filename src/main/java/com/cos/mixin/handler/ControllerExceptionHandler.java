package com.cos.mixin.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.mixin.dto.CMRespDto;
import com.cos.mixin.handler.ex.CustomValidationException;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	public CMRespDto<?> validationException(CustomValidationException e) {
		return new CMRespDto<>(-1, e.getMessage(), e.getErrorMap() );
	}
}
