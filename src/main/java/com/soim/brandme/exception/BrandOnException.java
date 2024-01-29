package com.soim.brandme.exception;

import lombok.Getter;

@Getter
public class BrandOnException extends IllegalArgumentException{
    private final ErrorCode errorCode;

    public BrandOnException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
