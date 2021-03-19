package com.adrian.interview.operation;

import com.adrian.interview.model.QueryRequestModel;
import com.adrian.interview.model.value.QueryOperation;
import com.adrian.interview.service.DataService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetOperation implements OperationInfo {

    private final DataService dataService;

    public QueryOperation getQueryOperationType() {
        return QueryOperation.FIND_ALL;
    }

    @Override
    public Object apply(QueryRequestModel requestModel) {
        return dataService.getDataWithPredicate(requestModel);
    }
}
