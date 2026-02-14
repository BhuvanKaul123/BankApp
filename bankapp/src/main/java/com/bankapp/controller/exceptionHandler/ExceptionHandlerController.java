package com.bankapp.controller.exceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.bankapp.dto.response.ErrorInfo;
import com.bankapp.exceptions.InvalidRequestException;
import com.bankapp.exceptions.ResourceNotFoundException;
import com.bankapp.exceptions.UsernameAlreadyExistsExceptions;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleBankTransactionNotFound (ResourceNotFoundException e){
		ErrorInfo errorInfo = ErrorInfo.builder()
						.errorMessage(e.getMessage())
						.statusCode(HttpStatus.NOT_FOUND.toString())
						.toContact("idk@ymsli")
						.timestamp(LocalDateTime.now())
						.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ErrorInfo> handleInvalidRequestException(InvalidRequestException e){
		ErrorInfo errorInfo = ErrorInfo.builder()
				.errorMessage(e.getMessage())
				.statusCode(HttpStatus.BAD_REQUEST.toString())
				.toContact("idk@ymsli")
				.timestamp(LocalDateTime.now())
				.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo);
	}
	
	@ExceptionHandler(UsernameAlreadyExistsExceptions.class)
	public ResponseEntity<ErrorInfo> handleInvalidRequestException(UsernameAlreadyExistsExceptions e){
		ErrorInfo errorInfo = ErrorInfo.builder()
				.errorMessage(e.getMessage())
				.statusCode(HttpStatus.CONFLICT.toString())
				.toContact("idk@ymsli")
				.timestamp(LocalDateTime.now())
				.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException e) {

        String errors = e.getBindingResult()
                          .getAllErrors()
                          .stream()
                          .map(error -> error.getDefaultMessage())
                          .collect(Collectors.joining("; "));

        ErrorInfo errorInfo = ErrorInfo.builder()
                .errorMessage(errors)
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .toContact("idk@ymsli")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorInfo> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

	    String message;
	    message = "Invalid value for parameter: " + ex.getName();

	    ErrorInfo errorInfo = ErrorInfo.builder()
	            .errorMessage(message)
	            .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
	            .toContact("idk@ymsli")
	            .timestamp(LocalDateTime.now())
	            .build();

	    return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
	    throw e; 
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> handle500InternalServerException(Exception e){
		ErrorInfo errorInfo = ErrorInfo.builder()
				.errorMessage(e.getMessage())
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
				.toContact("idk@ymsli")
				.timestamp(LocalDateTime.now())
				.build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
	}
}
