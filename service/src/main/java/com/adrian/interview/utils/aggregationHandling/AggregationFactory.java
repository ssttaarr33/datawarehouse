package com.adrian.interview.utils.aggregationHandling;

import com.adrian.interview.utils.aggregationHandling.collection.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AggregationFactory {
    static Map<Pair, AggregationType> aggregators = new HashMap<>();

    static {
        aggregators.put(Pair.of("dataSourceType", "campaign"), new ByDataSourceCampaign());
        aggregators.put(Pair.of("campaign", "dataSourceType"), new ByCampaignDataSource());
        aggregators.put(Pair.of("campaign", "clicks"), new ByCampaignClicks());
        aggregators.put(Pair.of("campaign", "impressions"), new ByCampaignImpressions());
        aggregators.put(Pair.of("dataSourceType", "impressions"), new ByDataSourceImpressions());
        aggregators.put(Pair.of("dataSourceType", "clicks"), new ByDataSourceClicks());
    }

    public static Optional<AggregationType> getAggregation(Pair pair) {
        return Optional.ofNullable(aggregators.get(pair));
    }
}
