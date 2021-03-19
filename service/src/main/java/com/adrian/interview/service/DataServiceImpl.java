package com.adrian.interview.service;

import com.adrian.interview.controller.queryModel.QueryRequestModel;
import com.adrian.interview.entity.RecordModel;
import com.adrian.interview.aggregationHandling.predicate.GenericSpecification;
import com.adrian.interview.repository.CampaignRepository;
import com.adrian.interview.aggregationHandling.FormulaProcessor;
import com.adrian.interview.utils.loader.LoadDataFromUrlToH2ByBulk;
import com.adrian.interview.utils.loader.LoadDataFromUrlToH2ByStep;
import com.adrian.interview.utils.loader.LoadDataInterface;
import com.adrian.interview.utils.misc.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private static Map<String, LoadDataInterface> strategies = new HashMap<>();

    private final CampaignRepository campaignRepository;

    public List<RecordModel> getAll() {
        return campaignRepository.findAll();
    }

    @Override
    public Object getDataWithPredicate(QueryRequestModel queryModel) {
        GenericSpecification specs = new GenericSpecification<RecordModel>();
        queryModel.getCriteriaPair().forEach(specs::add);
        return getData(queryModel, specs);
    }

    private Object getData(QueryRequestModel queryModel, GenericSpecification specs) {
        if (queryModel.getFormula2() != null) {
            return FormulaProcessor.processFormula(queryModel.getFormula2(), campaignRepository.findAll(specs));
        }
        if (queryModel.getFormula1() != null) {
            return FormulaProcessor.processFormula(queryModel.getFormula1(), campaignRepository.findAll(specs));
        }
        if (queryModel.getAggregation() != null) {
            return FormulaProcessor.postAggregate(campaignRepository.findAll(specs), queryModel.getAggregation());
        }
        return campaignRepository.findAll(specs);
    }

    @Override
    public Long countDataWithPredicate(QueryRequestModel queryModel) {
        GenericSpecification specs = new GenericSpecification<RecordModel>();
        queryModel.getCriteriaPair().forEach(specs::add);
        return campaignRepository.count(specs);
    }


    public boolean loadData(String url, String strategy) throws MalformedURLException {
        populateStrategies();
        return strategies.get(strategy).loadData(url, campaignRepository);
    }

    private void populateStrategies() {
        strategies.put(Constants.BY_BULK, new LoadDataFromUrlToH2ByBulk());
        strategies.put(Constants.BY_STEP, new LoadDataFromUrlToH2ByStep());
    }
}
