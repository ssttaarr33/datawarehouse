package com.adrian.interview.aggregationHandling.formula;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FormulaType1 {
    List<String> multiplier = new ArrayList<>();
    List<String> divisor = new ArrayList<>();
    int constant;
}
