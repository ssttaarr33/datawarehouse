package com.adrian.interview.operation;

import com.adrian.interview.model.QueryRequestModel;
import com.adrian.interview.service.DataService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CountOperation implements Operation {

    private DataService dataService;

    @Override
    public Object apply(QueryRequestModel requestModel) {
        return dataService.countDataWithPredicate(requestModel);
    }
}