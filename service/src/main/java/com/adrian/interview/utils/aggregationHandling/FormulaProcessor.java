package com.adrian.interview.utils.aggregationHandling;

import com.adrian.interview.model.AdditionalAggregation;
import com.adrian.interview.model.RecordModel;
import com.adrian.interview.model.formula.FormulaType1;
import com.adrian.interview.model.formula.FormulaType2;
import com.adrian.interview.utils.errorHandling.InvalidActionException;
import com.adrian.interview.utils.misc.Constants;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class FormulaProcessor {

    public static Object postAggregate(List all, AdditionalAggregation additionalAggregation) {
        AggregationType aggregate = AggregationFactory
                .getAggregation(Pair.of(additionalAggregation.getKey(), additionalAggregation.getAggregatedValue()))
                .orElseThrow(() -> new InvalidActionException("Invalid Aggregator"));
        return aggregate.apply(all);
    }

    public static Object processFormula(FormulaType2 formula2, List all) {
        Expression expression = new ExpressionBuilder(formula2.getExpression())
                .variables(formula2.getVariables()).build();
        formula2.getVariables().forEach(variable -> {
            expression.setVariable(variable, reflectValue(variable, all));
        });
        return expression.evaluate();
    }

    public static Object processFormula(FormulaType1 formula1, List all) {
        float multiplied = 1;
        float divided = 1;
        multiplied = computeMultiplier(all, multiplied, formula1.getMultiplier());
        divided = 1 / computeMultiplier(all, divided, formula1.getDivisor());
        float constant = formula1.getConstant();
        return constant * multiplied * divided;
    }

    private static float computeMultiplier(List all, float multiplied, List<String> numbers) {
        for (String namedMultiplier : numbers) {
            switch (namedMultiplier) {
                case Constants.CLICKS:
                    return multiplied * getTotalClicks(all);
                case Constants.IMPRESSIONS:
                    return multiplied * getTotalImpressions(all);
                default:
                    break;
            }
        }
        return multiplied;
    }

    private static int reflectValue(String name, List all) {
        try {
            String className = FormulaProcessor.class.getName();
            Class c = Class.forName(className);
            Method method1 = c.getDeclaredMethod(name, List.class);
            Object o = c.newInstance();
            return (int) method1.invoke(o, all);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(null);
    }

    private static int totalClicks(List all) {
        return getTotalClicks(all);
    }

    private static int totalImpressions(List all) {
        return getTotalImpressions(all);
    }

    private static int getTotalClicks(List all) {
        return (Integer) all.stream().map(rec -> ((RecordModel) rec).getClicks()).collect(Collectors.summingInt(Integer::intValue));
    }

    private static int getTotalImpressions(List all) {
        return (Integer) all.stream().map(rec -> ((RecordModel) rec).getImpressions()).collect(Collectors.summingInt(Integer::intValue));
    }
}
