package com.adrian.interview.operation;

import com.adrian.interview.controller.queryModel.QueryRequestModel;
import com.adrian.interview.aggregationHandling.value.QueryOperation;

public interface Operation {
    Object apply(QueryRequestModel requestModel);
    QueryOperation getQueryOperationType();
}
