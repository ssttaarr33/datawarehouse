package FormulaProcessorTest;

import com.adrian.interview.entity.RecordModel;
import com.adrian.interview.aggregationHandling.formula.FormulaType1;
import com.adrian.interview.aggregationHandling.formula.FormulaType2;
import com.adrian.interview.aggregationHandling.value.DataSourceType;
import com.adrian.interview.aggregationHandling.FormulaProcessor;
import com.adrian.interview.utils.misc.Constants;
import com.adrian.interview.utils.misc.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormulaProcessorTest {
    private static final String RANDOM_CAMPAIGN = "random campaign";
    private static final String RANDOM_DATE = "11/12/19";
    private static final int CLICKS = 5;
    private static final int IMPRESSIONS = 10;
    private static final int CONSTANT = 100;
    private static final String TOTAL_CLICKS = "totalClicks";
    private static final String TOTAL_IMPRESSIONS = "totalImpressions";
    private static final String EXPRESSION = "totalClicks*100/totalImpressions";


    private FormulaType1 formula1;
    private FormulaType2 formula2;
    private List<RecordModel> all;

    @BeforeEach
    void setup() {
        formula1 = new FormulaType1();
        formula1.setMultiplier(List.of(Constants.CLICKS));
        formula1.setDivisor(List.of(Constants.IMPRESSIONS));
        formula1.setConstant(CONSTANT);

        formula2 = new FormulaType2();
        formula2.setExpression(EXPRESSION);
        formula2.setVariables(Set.of(TOTAL_CLICKS, TOTAL_IMPRESSIONS));
        all = List.of(RecordModel.builder()
                .dataSourceType(DataSourceType.GOOGLE.toValue())
                .campaign(RANDOM_CAMPAIGN)
                .daily(Utils.textToDate(RANDOM_DATE))
                .clicks(CLICKS)
                .impressions(IMPRESSIONS)
                .build());
    }

    @Test
    @DisplayName("Test process formula 1")
    void testProcessFormula1() {
        assertEquals(50.0f, FormulaProcessor.processFormula(formula1, all));
    }

    @Test
    @DisplayName("Test process formula 2")
    void testProcessFormula2() {
        assertEquals(50.0, FormulaProcessor.processFormula(formula2, all));
    }
}
