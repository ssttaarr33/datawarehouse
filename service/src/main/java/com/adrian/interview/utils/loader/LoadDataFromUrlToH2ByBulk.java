package com.adrian.interview.utils.loader;

import com.adrian.interview.model.RecordModel;
import com.adrian.interview.model.value.DataSourceType;
import com.adrian.interview.repository.CampaignRepository;
import com.adrian.interview.utils.misc.Utils;
import com.adrian.interview.utils.errorHandling.Exceptions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LoadDataFromUrlToH2ByBulk implements LoadDataInterface {

    @Override
    public boolean loadData(String url, CampaignRepository campaignRepository) throws MalformedURLException {
        URL dataURL = new URL(url);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
        List<RecordModel> csvRecords = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(dataURL, StandardCharsets.UTF_8, csvFormat)) {
            csvParser.forEach(csvRecord -> csvRecords.add(Utils.recordBuilder(csvRecord)));
            campaignRepository.saveAll(csvRecords);
            return true;
        } catch (IOException e) {
            throw Exceptions.loadDataByBulkError().get();
        }
    }
}
