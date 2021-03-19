package com.adrian.interview.operation;

import com.adrian.interview.model.QueryRequestModel;
import com.adrian.interview.model.value.QueryOperation;

public interface Operation {
    Object apply(QueryRequestModel requestModel);
    QueryOperation getQueryOperationType();
}
