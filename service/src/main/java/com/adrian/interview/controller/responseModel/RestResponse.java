package com.adrian.interview.controller.responseModel;

import com.adrian.interview.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("RestResponse")
@EqualsAndHashCode
@ToString
public class RestResponse<T> {
    private final RestResponse.ServiceResponse service;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    private RestResponse(@JsonProperty("com/adrian/interview/service") RestResponse.ServiceResponse service, @JsonProperty("data") T data) {
        this.service = service;
        this.data = data;
    }

    public static <T> RestResponse ok(T data) {
        return new RestResponse(RestResponse.ServiceResponse.OK, data);
    }

    public static <T> RestResponse fail(ErrorCode errorCode, String errorDescription, T data) {
        return fail(errorCode.numericCode(), errorCode.stringCode(), errorDescription, data);
    }

    private static <T> RestResponse fail(long numericCode, String stringCode, String errorDescription, T data) {
        return new RestResponse(new RestResponse.ServiceResponse(numericCode, stringCode, errorDescription), data);
    }

    @ApiModelProperty("Custom data that will be returned with RestResponse")
    public T getData() {
        return this.data;
    }

    @ApiModel(
            value = "ServiceResponse",
            description = "Wrapper for com.adrian.interview.service response"
    )

    @EqualsAndHashCode
    @ToString
    public static class ServiceResponse {
        static final RestResponse.ServiceResponse OK = new RestResponse.ServiceResponse(0L, null, null);
        private final long code;
        @JsonProperty("error_type")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String errorType;
        @JsonProperty("error_description")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String errorDescription;

        ServiceResponse(@JsonProperty("code") long code, @JsonProperty("error_type") String errorType, @JsonProperty("error_description") String errorDescription) {
            this.code = code;
            this.errorType = errorType;
            this.errorDescription = errorDescription;
        }
    }
}