package com.assessment.search.util;

import com.assessment.search.dtos.SearchResponseData;
import com.assessment.search.repositories.SearchSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.assessment.search.util.SearchConstants.*;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class SearchUtil {

    public static <T> Specification<T> createSpecification(String query) {
        SearchSpecificationBuilder<T> specificationBuilder = new SearchSpecificationBuilder<T>();
        for (Matcher matcher = SEARCH_QUERY_PATTERN.matcher(query + COMMA); matcher.find(); ) {
            specificationBuilder.with(matcher.group(1), matcher.group(2),
                    matcher.group(3), matcher.group(4), matcher.group(5).replaceAll(DOUBLE_QUOTES, EMPTY));
        }
        return specificationBuilder.build();
    }

    public static <T> T createCopyObject(Object src, Supplier<T> supplier) {
        T destination = supplier.get();
        copyProperties(src, destination);
        return destination;
    }

    public static <T, R> SearchResponseData createResponse(Page<T> page, Function<T, R> mapper) {
        SearchResponseData responseData = new SearchResponseData();
        responseData.setPageIndex(page.getNumber());
        responseData.setCount(page.getNumberOfElements());
        responseData.setTotalPages(page.getTotalPages());
        responseData.setData((page.stream().map(mapper).collect(Collectors.toList())));
        return responseData;
    }

    public static List<Sort.Order> getOrders(List<String> sorts, String defaultProperty) {
        if(null == sorts || sorts.isEmpty()) {
            return Arrays.asList(new Sort.Order(Sort.Direction.DESC, defaultProperty));
        }

        List<Sort.Order> sortList = new ArrayList<>();
        for (String sort: sorts) {
            String[] split = sort.split(":");
            sortList.add(new Sort.Order(split.length > 1 ? ASC : DESC, split[0]));
        }
        return sortList;
    }

    public static String parse(String dateString) {
        DateFormat originalFormat = new SimpleDateFormat("MMMMM dd, yyyy");
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = originalFormat.parse(dateString);
        } catch(ParseException ex) {
            ex.printStackTrace();
        }
        if (null == date) {
            return "";
        }
        return targetFormat.format(date);
    }

    public static void main(String[] args) {
        System.out.println((2 ^ 3) >>> 16);
    }
}
