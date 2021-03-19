package com.adrian.interview.utils.misc;

import com.adrian.interview.entity.RecordModel;
import com.adrian.interview.aggregationHandling.value.DataSourceType;
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
                .dataSourceType(DataSourceType.fromValue(csvRecord.get(Constants.DATA_SOURCE_TYPE_FIELD)).toValue())
                .campaign(csvRecord.get(Constants.CAMPAIGN_FIELD))
                .daily(Utils.textToDate(csvRecord.get(Constants.DAILY_FIELD)))
                .clicks(Integer.parseInt(csvRecord.get(Constants.CLICKS_FIELD)))
                .impressions(Integer.parseInt(csvRecord.get(Constants.IMPRESSIONS_FIELD)))
                .build();
    }
}
