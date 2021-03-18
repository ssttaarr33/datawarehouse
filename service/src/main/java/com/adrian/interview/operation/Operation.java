package com.adrian.interview.operation;

import com.adrian.interview.model.QueryRequestModel;

public interface Operation {
    Object apply(QueryRequestModel requestModel);
}
