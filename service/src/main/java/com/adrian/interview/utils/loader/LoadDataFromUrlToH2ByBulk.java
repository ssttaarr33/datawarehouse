package com.adrian.interview.utils.loader;

import com.adrian.interview.entity.RecordModel;
import com.adrian.interview.repository.CampaignRepository;
import com.adrian.interview.exception.exceptionHandling.Exceptions;
import com.adrian.interview.utils.misc.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            log.error("Error while loading data from: {}", this.getClass().getName());
            throw Exceptions.loadDataByBulkError().get();
        }
    }
}
