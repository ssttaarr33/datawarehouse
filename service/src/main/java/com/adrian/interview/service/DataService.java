package com.adrian.interview.service;

import com.adrian.interview.controller.queryModel.QueryRequestModel;
import com.adrian.interview.entity.RecordModel;

import java.net.MalformedURLException;
import java.util.List;

public interface DataService {
    List<RecordModel> getAll();
    Object getDataWithPredicate(QueryRequestModel queryModel);
    Long countDataWithPredicate(QueryRequestModel queryModel);

    boolean loadData(String url, String strategy) throws MalformedURLException;

}
