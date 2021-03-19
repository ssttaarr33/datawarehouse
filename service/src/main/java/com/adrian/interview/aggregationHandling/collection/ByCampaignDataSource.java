package com.adrian.interview.aggregationHandling.collection;

import com.adrian.interview.entity.RecordModel;
import com.adrian.interview.aggregationHandling.AggregationType;

import java.util.List;
import java.util.stream.Collectors;

public class ByCampaignDataSource implements AggregationType {
    @Override
    public Object apply(List all) {
        return all.stream().collect(Collectors.groupingBy(RecordModel::getCampaign, Collectors.groupingBy(RecordModel::getDataSourceType)));
    }
}
