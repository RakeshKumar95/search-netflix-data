package com.assessment.search.dtos;

import com.assessment.search.util.SearchTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private boolean isAndCondition;
    private SearchTypeEnum type;
    private String key;
    private String comparator;
    private String value;

    public SearchCriteria(String isAndCondition, String type, String key, String comparator, String value) {
        this.isAndCondition = StringUtils.isEmpty(isAndCondition);
        this.key = key;
        this.value = value;
        this.type = SearchTypeEnum.getValueByType(type);
        this.comparator = comparator;
    }
}
