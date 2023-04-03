package com.cos.mixin.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cos.mixin.dto.ResponseDto;
import com.cos.mixin.handler.ex.CustomApiException;
import com.cos.mixin.handler.ex.CustomForbiddenException;
import com.cos.mixin.handler.ex.CustomValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		log.error(e.getMessage());
		return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomForbiddenException.class)
	public ResponseEntity<?> fobiddenException(CustomForbiddenException e) {
		log.error(e.getMessage());
		return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(CustomValidationException.class)
	public ResponseEntity<?> validationApiException(CustomValidationException e) {
		log.error(e.getMessage());
		return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
	}
}
