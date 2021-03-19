package com.adrian.interview.config;

import com.adrian.interview.model.value.QueryOperation;
import com.adrian.interview.operation.CountOperation;
import com.adrian.interview.operation.GetOperation;
import com.adrian.interview.operation.Operation;
import com.adrian.interview.operation.OperationInfo;
import com.adrian.interview.service.DataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class Config {
    @Bean
    public CountOperation getCountOperation(DataService dataService){
        return new CountOperation(dataService);
    }
    @Bean
    public GetOperation getGetOperation(DataService dataService){
        return new GetOperation(dataService);
    }

    @Bean
    public Map<QueryOperation, Operation> getOperation(List<OperationInfo> operations) {
        return operations
                .stream()
                .collect(Collectors.toMap(OperationInfo::getQueryOperationType, Function.identity(),
                        (existing, replacement) -> existing));
    }
}
