package com.capstone.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.capstone.employee.exception.InvalidEmployeeException;

@ControllerAdvice
public class EmployeeExceptionController {
	
	@ExceptionHandler(InvalidEmployeeException.class)
	public ResponseEntity<String> invalidExceptionHandler(InvalidEmployeeException exp){
		return new ResponseEntity<String>(exp.getMessage().toString(),HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception exp){
		return new ResponseEntity<String>(exp.getMessage().toString(),HttpStatus.EXPECTATION_FAILED);
	}
	

}
