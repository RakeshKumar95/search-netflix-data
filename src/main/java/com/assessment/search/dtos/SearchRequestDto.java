package com.assessment.search.dtos;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.assessment.search.util.SearchConstants.*;

@Data
public class SearchRequestDto {
    private String query;
    private List<String> sorts = new ArrayList<>();
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageIndex = DEFAULT_PAGE_INDEX;
}
