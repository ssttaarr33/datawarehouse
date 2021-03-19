package com.adrian.interview.aggregationHandling.predicate;

import com.adrian.interview.aggregationHandling.value.FilterOperation;
import lombok.Data;

@Data
public class Criteria {
    private String key;
    private Object value;
    private FilterOperation operation;
}
