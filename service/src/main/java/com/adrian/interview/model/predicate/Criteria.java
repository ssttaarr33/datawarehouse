package com.adrian.interview.model.predicate;

import com.adrian.interview.model.value.FilterOperation;
import lombok.Data;

@Data
public class Criteria {
    private String key;
    private Object value;
    private FilterOperation operation;
}
