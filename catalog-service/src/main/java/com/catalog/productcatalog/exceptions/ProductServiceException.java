package com.catalog.productcatalog.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductServiceException {
	
	
	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<?> handleException(RuntimeException ex){
		
		return ResponseEntity.badRequest().body(ex.getMessage());	
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<?> handleException(Exception ex){
		
		return ResponseEntity.badRequest().body(ex.getMessage());	
	}
}
