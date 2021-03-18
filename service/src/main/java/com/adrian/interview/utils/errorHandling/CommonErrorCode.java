package com.adrian.interview.utils.errorHandling;

public enum CommonErrorCode implements ErrorCode {
    ERROR_INTERNAL_ERROR(-1L),
    ERROR_NOT_SUPPORTED(-11L);

    private final long numericCode;

    private CommonErrorCode(long numericCode) {
        this.numericCode = numericCode;
    }

    public long numericCode() {
        return this.numericCode;
    }

    public String stringCode() {
        return this.name();
    }
}