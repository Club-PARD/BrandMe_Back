package com.soim.brandme.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BrandOnExceptionHandler extends ResponseEntityExceptionHandler {
//    Runtime Exception
    @ExceptionHandler(BrandOnException.class)
    public ResponseEntity<?> handleBrandOnException(BrandOnException e){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(LocalDateTime.now(),e.getErrorCode().getStatus(), e.getMessage());
        return new ResponseEntity<>(exceptionResponseDto, exceptionResponseDto.getStatus());
    }

}
