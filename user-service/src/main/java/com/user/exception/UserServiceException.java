package com.user.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserServiceException {
	
	@ExceptionHandler(value = {VerifyException.class})
	public ResponseEntity<?> handleException(VerifyException ex){
		
		return ResponseEntity.badRequest().body(ex.getMessage());	
	}
	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<?> handleException(RuntimeException ex){
		
		return ResponseEntity.badRequest().body(ex.getMessage());	
	}
}
