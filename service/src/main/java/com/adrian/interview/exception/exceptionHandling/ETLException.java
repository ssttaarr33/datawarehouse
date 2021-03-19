package com.adrian.interview.exception.exceptionHandling;

import com.adrian.interview.exception.ErrorCode;

public class ETLException extends RuntimeException {
    private static final long serialVersionUID = -3306027032830411569L;

    private final ErrorCode errorCode;

    public ETLException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
