package com.adrian.interview.model;

import com.adrian.interview.model.formula.FormulaType1;
import com.adrian.interview.model.formula.FormulaType2;
import com.adrian.interview.model.predicate.Criteria;
import com.adrian.interview.model.value.QueryOperation;
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
