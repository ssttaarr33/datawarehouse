package com.adrian.interview.utils.misc;

import com.adrian.interview.model.RecordModel;
import com.adrian.interview.model.value.DataSourceType;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static LocalDate textToDate(String text) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yy");
        return LocalDate.parse(text, df);
    }

    public static RecordModel recordBuilder(CSVRecord csvRecord){
        return RecordModel.builder()
                .dataSourceType(DataSourceType.fromValue(csvRecord.get("Datasource")).toValue())
                .campaign(csvRecord.get("Campaign"))
                .daily(Utils.textToDate(csvRecord.get("Daily")))
                .clicks(Integer.parseInt(csvRecord.get("Clicks")))
                .impressions(Integer.parseInt(csvRecord.get("Impressions")))
                .build();
    }
}
