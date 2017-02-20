package com.hackathon.error.handler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> hibernateValidationExceptions(MethodArgumentNotValidException ex) {

		Message msgErrors = new Message();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			msgErrors.addError(fieldError.getDefaultMessage());
		}

		return new ResponseEntity<>(msgErrors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> constraintViolationExceptions(ConstraintViolationException ex) {
		Message msgErrors = new Message();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			msgErrors.addError(violation.getMessage());
		}
		return new ResponseEntity<>(msgErrors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> otherExceptions(Exception ex) {
		Message msgErrors = new Message();
		msgErrors.addError("Ocorreu um erro.");
		return new ResponseEntity<>(msgErrors, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
