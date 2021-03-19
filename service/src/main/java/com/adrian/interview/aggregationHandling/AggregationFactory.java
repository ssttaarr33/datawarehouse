package com.adrian.interview.aggregationHandling;

import com.adrian.interview.aggregationHandling.collection.*;
import com.adrian.interview.utils.misc.Constants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

final class AggregationFactory {
    private static Map<Pair, AggregationType> aggregators = new HashMap<>();

    static {
        aggregators.put(Pair.of(Constants.DATA_SOURCE_TYPE, Constants.CAMPAIGN), new ByDataSourceCampaign());
        aggregators.put(Pair.of(Constants.CAMPAIGN, Constants.DATA_SOURCE_TYPE), new ByCampaignDataSource());
        aggregators.put(Pair.of(Constants.CAMPAIGN, Constants.CLICKS), new ByCampaignClicks());
        aggregators.put(Pair.of(Constants.CAMPAIGN, Constants.IMPRESSIONS), new ByCampaignImpressions());
        aggregators.put(Pair.of(Constants.DATA_SOURCE_TYPE, Constants.IMPRESSIONS), new ByDataSourceImpressions());
        aggregators.put(Pair.of(Constants.DATA_SOURCE_TYPE, Constants.CLICKS), new ByDataSourceClicks());
    }

    static Optional<AggregationType> getAggregation(Pair pair) {
        return Optional.ofNullable(aggregators.get(pair));
    }
}
