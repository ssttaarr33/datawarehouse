package com.adrian.interview.controller;

import com.adrian.interview.model.QueryRequestModel;
import com.adrian.interview.operation.Operation;
import com.adrian.interview.operation.OperationFactory;
import com.adrian.interview.service.DataService;
import com.adrian.interview.utils.errorHandling.ETLException;
import com.adrian.interview.utils.errorHandling.Exceptions;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.adrian.interview.utils.responseModel.RestResponse;

import javax.validation.constraints.NotEmpty;
import java.net.MalformedURLException;
import java.util.function.Supplier;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(path = "${app.endpoint.api}", produces = APPLICATION_JSON_VALUE)
public class DataController<T> {

    @Autowired
    private DataService dataService;
    @Autowired
    private OperationFactory operationFactory;

    @CrossOrigin(origins = "${app.allowed.origin.localhost}")
    @GetMapping("${app.get.data}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get data")
    public RestResponse<T> query() {
        return (RestResponse<T>) RestResponse.ok(dataService.getAll());
    }

    @CrossOrigin(origins = "${app.allowed.origin.localhost}")
    @GetMapping(value = "${app.load.data}/{strategy}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Load data")
    public RestResponse<T> loadData(@NotEmpty @PathVariable("strategy") String strategy, @RequestParam(defaultValue = "${app.data.location}") String url) {
        try {
            return (RestResponse<T>) RestResponse.ok(dataService.loadData(url, strategy));
        } catch (MalformedURLException e) {
            Supplier<ETLException> etlExceptionSupplier = Exceptions.malformedUrl(url);
            return (RestResponse<T>) RestResponse.fail(etlExceptionSupplier.get().getErrorCode(), "FAIL: " + etlExceptionSupplier.get().getMessage(), null);
        }
    }

    @CrossOrigin(origins = "${app.allowed.origin.localhost}")
    @PostMapping(value = "${app.get.custom.data}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get custom data")
    public RestResponse<T> getQueryData(@RequestBody QueryRequestModel requestModel) {
        Operation targetOperation = operationFactory
                .getOperation(requestModel.getAction())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operator!"));
        return (RestResponse<T>) RestResponse.ok(targetOperation.apply(requestModel));
    }

}
