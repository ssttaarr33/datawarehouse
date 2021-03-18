package com.adrian.interview.utils.loader;

import com.adrian.interview.model.value.DataSourceType;
import com.adrian.interview.model.RecordModel;
import com.adrian.interview.repository.CampaignRepository;
import com.adrian.interview.utils.errorHandling.Exceptions;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader {

    private CampaignRepository campaignRepository;

    public boolean loadDataFromUrlToH2ByStep(String url) throws MalformedURLException {
        URL dataURL = new URL(url);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
        try (CSVParser csvParser = CSVParser.parse(dataURL, StandardCharsets.UTF_8, csvFormat)) {
            csvParser.forEach(csvRecord -> {
                campaignRepository.saveAndFlush(RecordModel.builder()
                        .dataSourceType(DataSourceType.fromValue(csvRecord.get("Datasource")).toValue())
                        .campaign(csvRecord.get("Campaign"))
                        .daily(textToDate(csvRecord.get("Daily")))
                        .clicks(Integer.parseInt(csvRecord.get("Clicks")))
                        .impressions(Integer.parseInt(csvRecord.get("Impressions")))
                        .build());
            });
            return true;
        } catch (IOException e) {
            throw Exceptions.loadDataByStepError().get();
        }
    }

    public boolean loadDataFromUrlToH2ByBulk(String url) throws MalformedURLException {
        URL dataURL = new URL(url);
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
        List<RecordModel> csvRecords = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(dataURL, StandardCharsets.UTF_8, csvFormat)) {
            csvParser.forEach(csvRecord -> {
                csvRecords.add(RecordModel.builder()
                        .dataSourceType(DataSourceType.fromValue(csvRecord.get("Datasource")).toValue())
                        .campaign(csvRecord.get("Campaign"))
                        .daily(textToDate(csvRecord.get("Daily")))
                        .clicks(Integer.parseInt(csvRecord.get("Clicks")))
                        .impressions(Integer.parseInt(csvRecord.get("Impressions")))
                        .build());
            });
            campaignRepository.saveAll(csvRecords);
            return true;
        } catch (IOException e) {
            throw Exceptions.loadDataByBulkError().get();
        }
    }

    private LocalDate textToDate(String text) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yy");
        return LocalDate.parse(text, df);
    }

}
