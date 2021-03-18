package com.adrian.interview.utils.aggregationHandling.collection;

import com.adrian.interview.model.RecordModel;
import com.adrian.interview.utils.aggregationHandling.AggregationType;

import java.util.List;
import java.util.stream.Collectors;

public class ByDataSourceClicks implements AggregationType {
    @Override
    public Object apply(List all) {
        return all.stream().collect(Collectors.groupingBy(RecordModel::getDataSourceType, Collectors.summingInt(RecordModel::getClicks)));
    }
}
