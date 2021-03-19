package com.adrian.interview.operation;

import com.adrian.interview.model.QueryRequestModel;
import com.adrian.interview.model.value.QueryOperation;
import com.adrian.interview.service.DataService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountOperation implements OperationInfo {

    private final DataService dataService;

    public QueryOperation getQueryOperationType() {
        return QueryOperation.COUNT;
    }

    @Override
    public Object apply(QueryRequestModel requestModel) {
        return dataService.countDataWithPredicate(requestModel);
    }
}