package com.adrian.interview.utils.aggregationHandling;

import java.util.List;

public interface AggregationType {
    Object apply(List all);
}
