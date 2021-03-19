package com.adrian.interview.controller.queryModel;

import com.adrian.interview.aggregationHandling.model.AdditionalAggregation;
import com.adrian.interview.aggregationHandling.formula.FormulaType1;
import com.adrian.interview.aggregationHandling.formula.FormulaType2;
import com.adrian.interview.aggregationHandling.predicate.Criteria;
import com.adrian.interview.aggregationHandling.value.QueryOperation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueryRequestModel {
    QueryOperation action;
    AdditionalAggregation aggregation;
    FormulaType1 formula1;
    FormulaType2 formula2;
    List<Criteria> criteriaPair = new ArrayList<>();
}
