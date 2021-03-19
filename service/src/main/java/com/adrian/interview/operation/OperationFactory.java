package com.adrian.interview.operation;

import com.adrian.interview.aggregationHandling.value.QueryOperation;
import com.adrian.interview.service.DataService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OperationFactory {
    private Map<QueryOperation, Operation> operationMap;
    private DataService dataService;

    public OperationFactory(DataService dataService) {
        this.dataService = dataService;
        this.operationMap = initOperations();
    }

    private Map<QueryOperation, Operation> initOperations() {
        Map<QueryOperation, Operation> operationMap = new HashMap<>();
        operationMap.put(QueryOperation.COUNT, new CountOperation(dataService));
        operationMap.put(QueryOperation.FIND_ALL, new GetOperation(dataService));
        return operationMap;
    }

    public Optional<Operation> getOperation(QueryOperation operation) {
        return Optional.ofNullable(operationMap.get(operation));
    }
}
