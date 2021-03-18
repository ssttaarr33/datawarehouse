package com.adrian.interview.utils.aggregationHandling;

import com.adrian.interview.model.AdditionalAggregation;
import com.adrian.interview.model.RecordModel;
import com.adrian.interview.model.formula.FormulaType1;
import com.adrian.interview.model.formula.FormulaType2;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class FormulaProcessor {

    //TODO - replace postAggregate with something fancier
    public static Object postAggregate(List all, AdditionalAggregation additionalAggregation) {
        if (additionalAggregation.getKey().equals("dataSourceType") && additionalAggregation.getAggregatedValue().equals("campaign")) {
            return all.stream().collect(Collectors.groupingBy(RecordModel::getDataSourceType, Collectors.groupingBy(RecordModel::getCampaign)));
        } else if (additionalAggregation.getKey().equals("campaign") && additionalAggregation.getAggregatedValue().equals("dataSourceType")) {
            return all.stream().collect(Collectors.groupingBy(RecordModel::getCampaign, Collectors.groupingBy(RecordModel::getDataSourceType)));
        } else if (additionalAggregation.getKey().equals("dataSourceType") && additionalAggregation.getAggregatedValue().equals("clicks")) {
            return all.stream().collect(Collectors.groupingBy(RecordModel::getDataSourceType, Collectors.summingInt(RecordModel::getClicks)));
        } else if (additionalAggregation.getKey().equals("dataSourceType") && additionalAggregation.getAggregatedValue().equals("impressions")) {
            return all.stream().collect(Collectors.groupingBy(RecordModel::getDataSourceType, Collectors.summingInt(RecordModel::getImpressions)));
        } else if (additionalAggregation.getKey().equals("campaign") && additionalAggregation.getAggregatedValue().equals("clicks")) {
            return all.stream().collect(Collectors.groupingBy(RecordModel::getCampaign, Collectors.summingInt(RecordModel::getClicks)));
        } else if (additionalAggregation.getKey().equals("campaign") && additionalAggregation.getAggregatedValue().equals("impressions")) {
            return all.stream().collect(Collectors.groupingBy(RecordModel::getCampaign, Collectors.summingInt(RecordModel::getImpressions)));
        } else {
            return null;
        }
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
                case "clicks":
                    return multiplied * getTotalClicks(all);
                case "impressions":
                    return multiplied * getTotalImpressions(all);
                default:
                    break;
            }
        }
        return multiplied;
    }

    private static int reflectValue(String name, List all){
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

    private static int totalClicks(List all){
        return getTotalClicks(all);
    }

    private static int totalImpressions(List all){
        return getTotalImpressions(all);
    }

    private static int getTotalClicks(List all) {
        return (Integer) all.stream().map(rec -> ((RecordModel) rec).getClicks()).collect(Collectors.summingInt(Integer::intValue));
    }

    private static int getTotalImpressions(List all) {
        return (Integer) all.stream().map(rec -> ((RecordModel) rec).getImpressions()).collect(Collectors.summingInt(Integer::intValue));
    }
}
