package com.adrian.interview.aggregationHandling.formula;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class FormulaType2 {
    String expression;
    Set<String> variables = new HashSet<>();
}
