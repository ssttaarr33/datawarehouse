package com.adrian.interview.aggregationHandling;

import java.util.List;

public interface AggregationType {
    Object apply(List all);
}
