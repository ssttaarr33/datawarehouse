import com.adrian.interview.AppStarter;
import com.adrian.interview.aggregationHandling.formula.FormulaType2;
import com.adrian.interview.aggregationHandling.predicate.Criteria;
import com.adrian.interview.aggregationHandling.value.DataSourceType;
import com.adrian.interview.aggregationHandling.value.FilterOperation;
import com.adrian.interview.aggregationHandling.value.QueryOperation;
import com.adrian.interview.controller.queryModel.QueryRequestModel;
import com.adrian.interview.entity.RecordModel;
import com.adrian.interview.repository.CampaignRepository;
import com.adrian.interview.utils.misc.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = AppStarter.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@AutoConfigureTestDatabase
public class DataControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CampaignRepository campaignRepository;

    private static final String RANDOM_CAMPAIGN = "random campaign";
    private static final String RANDOM_DATE = "11/12/19";
    private static final int CLICKS = 5;
    private static final int IMPRESSIONS = 10;
    private static final String RANDOM_CAMPAIGN1 = "random campaign1";
    private static final String RANDOM_DATE1 = "10/12/19";
    private static final int CLICKS1 = 6;
    private static final int IMPRESSIONS1 = 20;
    private static final String TOTAL_CLICKS = "totalClicks";
    private static final String TOTAL_IMPRESSIONS = "totalImpressions";
    private static final String EXPRESSION = "totalClicks*100/totalImpressions";

    @Test
    public void givenLoadByBulk_whenLoadData_thenStatus200()
            throws Exception {

        mvc.perform(get("/data-api/load/byBulk").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    public void givenLoadByUndefined_whenLoadData_thenStatus400()
            throws Exception {

        mvc.perform(get("/data-api/load/undef").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenGetAllData_whenGetAllData_thenStatus200()
            throws Exception {
        createTestData(createRecordModel(DataSourceType.GOOGLE.toValue(), RANDOM_CAMPAIGN, RANDOM_DATE, CLICKS, IMPRESSIONS));
        createTestData(createRecordModel(DataSourceType.GOOGLE.toValue(), RANDOM_CAMPAIGN1, RANDOM_DATE1, CLICKS1, IMPRESSIONS1));
        mvc.perform(get("/data-api/query").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.data[0].clicks").value(5))
                .andExpect(jsonPath("$.data[1].dataSourceType").value(DataSourceType.GOOGLE.toValue()));
    }

    @Test
    public void givenGetSpecificData_whenPostForCustomData_thenStatus200()
            throws Exception {
        createTestData(createRecordModel(DataSourceType.GOOGLE.toValue(), RANDOM_CAMPAIGN, RANDOM_DATE, CLICKS, IMPRESSIONS));
        createTestData(createRecordModel(DataSourceType.TWITTER.toValue(), RANDOM_CAMPAIGN1, RANDOM_DATE1, CLICKS1, IMPRESSIONS1));

        mvc.perform(post("/data-api/customQuery").contentType(MediaType.APPLICATION_JSON).content(toJson(createQuery())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.data").value(40.0));
    }

    private QueryRequestModel createQuery() {
        FormulaType2 formulaType2 = new FormulaType2();
        formulaType2.setExpression(EXPRESSION);
        formulaType2.setVariables(Set.of(TOTAL_CLICKS, TOTAL_IMPRESSIONS));
        Criteria criteria = new Criteria();
        criteria.setKey("dataSourceType");
        criteria.setValue("Google");
        criteria.setOperation(FilterOperation.MATCH);
        QueryRequestModel requestModel = new QueryRequestModel();
        requestModel.setAction(QueryOperation.FIND_ALL);
        requestModel.setFormula2(formulaType2);
        requestModel.getCriteriaPair().add(criteria);
        return requestModel;
    }

    private RecordModel createRecordModel(String dataSource, String campaign, String date, int clicks, int impressions) {
        return RecordModel.builder()
                .dataSourceType(dataSource)
                .campaign(campaign)
                .daily(Utils.textToDate(date))
                .clicks(clicks)
                .impressions(impressions)
                .build();
    }

    private void createTestData(RecordModel recordModel) {
        campaignRepository.saveAndFlush(recordModel);
    }

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
