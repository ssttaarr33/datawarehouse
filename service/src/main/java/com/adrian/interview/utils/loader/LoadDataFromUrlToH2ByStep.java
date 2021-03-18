package com.adrian.interview.utils.loader;

import com.adrian.interview.repository.CampaignRepository;
import com.adrian.interview.utils.errorHandling.Exceptions;
import com.adrian.interview.utils.misc.Utils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoadDataFromUrlToH2ByStep implements LoadDataInterface {
    @Override
    public boolean loadData(String url, CampaignRepository campaignRepository) throws MalformedURLException {
        URL dataURL = new URL(url);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
        try (CSVParser csvParser = CSVParser.parse(dataURL, StandardCharsets.UTF_8, csvFormat)) {
            csvParser.forEach(csvRecord -> campaignRepository.saveAndFlush(Utils.recordBuilder(csvRecord)));
            return true;
        } catch (IOException e) {
            throw Exceptions.loadDataByStepError().get();
        }
    }
}
