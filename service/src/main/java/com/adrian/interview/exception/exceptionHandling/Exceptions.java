package com.adrian.interview.exception.exceptionHandling;

import java.util.function.Supplier;

import static com.adrian.interview.exception.CommonErrorCode.ERROR_INTERNAL_ERROR;
import static com.adrian.interview.exception.CommonErrorCode.ERROR_NOT_SUPPORTED;

public final class Exceptions {
    private Exceptions() {
    }

    public static Supplier<ETLException> loadDataByStepError() {
        return () -> new ETLException(ERROR_INTERNAL_ERROR, "Error while loading data step by step");
    }

    public static Supplier<ETLException> loadDataByBulkError() {
        return () -> new ETLException(ERROR_INTERNAL_ERROR, "Error while loading data by bulk");
    }

    public static Supplier<ETLException> malformedUrl(String url) {
        return () -> new ETLException(ERROR_INTERNAL_ERROR, String.format("Malformed url: %s", url));
    }

    public static Supplier<ETLException> operationNotSupported() {
        return () -> new ETLException(ERROR_NOT_SUPPORTED, String.format("OPERATION NOT SUPPORTED"));
    }
}
