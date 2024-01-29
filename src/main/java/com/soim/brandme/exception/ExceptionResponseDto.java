package com.soim.brandme.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponseDto {
    private LocalDateTime time;
    private HttpStatus status;
    private String message;
}
