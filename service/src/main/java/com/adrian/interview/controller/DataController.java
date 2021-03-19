package com.adrian.interview.controller;

import com.adrian.interview.controller.queryModel.QueryRequestModel;
import com.adrian.interview.controller.responseModel.RestResponse;
import com.adrian.interview.exception.InvalidActionException;
import com.adrian.interview.exception.exceptionHandling.ETLException;
import com.adrian.interview.exception.exceptionHandling.Exceptions;
import com.adrian.interview.operation.Operation;
import com.adrian.interview.operation.OperationFactory;
import com.adrian.interview.service.DataService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.net.MalformedURLException;
import java.util.function.Supplier;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.endpoint.api}", produces = APPLICATION_JSON_VALUE)
public class DataController<T> {

    private final DataService dataService;
    private final OperationFactory operationFactory;

    @CrossOrigin(origins = "${app.allowed.origin.localhost}")
    @GetMapping("${app.get.data}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get data")
    public RestResponse<T> query() {
        log.info("Get all data");
        return (RestResponse<T>) RestResponse.ok(dataService.getAll());
    }

    @CrossOrigin(origins = "${app.allowed.origin.localhost}")
    @GetMapping(value = "${app.load.data}/{strategy}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Load data")
    public RestResponse<T> loadData(@NotEmpty @PathVariable("strategy") String strategy, @RequestParam(defaultValue = "${app.data.location}") String url) {
        try {
            log.info("Load data by strategy: {}", strategy);
            return (RestResponse<T>) RestResponse.ok(dataService.loadData(url, strategy));
        } catch (MalformedURLException e) {
            log.error("Failed to load data by strategy: {}", strategy);
            Supplier<ETLException> etlExceptionSupplier = Exceptions.malformedUrl(url);
            return (RestResponse<T>) RestResponse.fail(etlExceptionSupplier.get().getErrorCode(), "FAIL: " + etlExceptionSupplier.get().getMessage(), null);
        }
    }

    @CrossOrigin(origins = "${app.allowed.origin.localhost}")
    @PostMapping(value = "${app.get.custom.data}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Get custom data")
    public RestResponse<T> getQueryData(@RequestBody QueryRequestModel requestModel) {
        log.info("Get data by custom query: {}", requestModel.toString());
        Operation targetOperation = operationFactory
                .getOperation(requestModel.getAction())
                .orElseThrow(() -> {
                    log.error("Invalid query operation: {}", requestModel.getAction());
                    return new InvalidActionException("Invalid Action!");
                });
        return (RestResponse<T>) RestResponse.ok(targetOperation.apply(requestModel));
    }

}
