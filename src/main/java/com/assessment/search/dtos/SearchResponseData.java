package com.assessment.search.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponseData {
    private int pageIndex;
    private long count;
    private long totalPages;
    private List<Object> data;
}
