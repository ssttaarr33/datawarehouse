package com.adrian.interview.model.value;

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
        if (value != null) {
            for (DataSourceType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return getDefault();
    }

    private static DataSourceType getDefault() {
        return OTHER;
    }

    public String toValue(){
        return value;
    }
}
