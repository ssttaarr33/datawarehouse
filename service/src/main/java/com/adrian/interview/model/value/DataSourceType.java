package com.adrian.interview.model.value;

import java.util.Arrays;

public enum DataSourceType {
    GOOGLE("Google Ads"),
    TWITTER("Twitter Ads"),
    FACEBOOK("Facebook Ads"),
    OTHER("other");
    private final String value;

    DataSourceType(String value) {
        this.value = value;
    }

    public static DataSourceType fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equals(value))
                .findFirst()
                .orElseGet(DataSourceType::getDefault);
    }

    private static DataSourceType getDefault() {
        return OTHER;
    }

    public String toValue() {
        return value;
    }
}
