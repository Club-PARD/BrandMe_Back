package com.soim.brandme.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
//    예외처리하고 싶은 에러들 정의
    USERID_NOT_FOUND(HttpStatus.NOT_FOUND,"userID에 맞는 유저정보가 없습니다");

    private HttpStatus status;
    private String message;
}
