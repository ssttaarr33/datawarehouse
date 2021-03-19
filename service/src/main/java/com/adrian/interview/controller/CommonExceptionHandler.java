package com.adrian.interview.controller;

import com.adrian.interview.exception.CommonErrorCode;
import com.adrian.interview.exception.exceptionHandling.ETLException;
import com.adrian.interview.exception.InvalidActionException;
import com.adrian.interview.controller.responseModel.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@ControllerAdvice(assignableTypes = DataController.class)
@RestController
@Order(HIGHEST_PRECEDENCE)
public class CommonExceptionHandler {

    @ExceptionHandler(MalformedURLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse handleURLException(MalformedURLException exception) {
        log.error("Malformed url exception");
        return RestResponse.fail(CommonErrorCode.ERROR_INTERNAL_ERROR, exception.getMessage(), null);
    }

    @ExceptionHandler(ETLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse handleETLException(ETLException exception) {
        log.error("ETL exception");
        return RestResponse.fail(CommonErrorCode.ERROR_INTERNAL_ERROR, exception.getMessage(), null);
    }

    @ExceptionHandler(InvalidActionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse handleActionException(InvalidActionException exception) {
        log.error("Action not supported");
        return RestResponse.fail(CommonErrorCode.ERROR_NOT_SUPPORTED, exception.getMessage(), null);
    }
}
