package com.fedrikp.exception;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fedrikp.dto.error.ErrorResponse;

//class ini berfungsi sebagai centralized exception handler
//sehingga response error konsisten di API
@RestControllerAdvice
public class GlobalExceptionHandler {

	//handle ketika resource tidak ditemukan, kode error 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

    	ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //handle ketika request tidak sesuai format yang diinginkan, kode error 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {

    	ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //handle ketika terjadi duplikasi data, kode error 409
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex) {

    	ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());

    	return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    // handle ketika validasi @Valid gagal, kode error 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
    	String message = ex.getBindingResult()
    			.getFieldErrors()
    			.stream()
    			.map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

    	ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}