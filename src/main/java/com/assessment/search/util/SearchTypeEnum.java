package com.assessment.search.util;

import lombok.Getter;

@Getter
public enum SearchTypeEnum {
    NUM_PARAM("N-"),
    STRING_PARAM("S-"),
    DATE_PARAM("D-");

    private final String type;

    SearchTypeEnum(String type) {
        this.type = type;
    }

    public static SearchTypeEnum getValueByType(String type) {
        for(SearchTypeEnum e: values()) {
            if (e.getType().equals(type)) {
                return e;
            }
        }
        return null;
    }
}
