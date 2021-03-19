package com.adrian.interview.operation;

import com.adrian.interview.model.value.QueryOperation;

public interface OperationInfo extends Operation {
    QueryOperation getQueryOperationType();
}
