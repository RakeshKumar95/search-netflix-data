package com.assessment.search.repositories;

import com.assessment.search.dtos.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchSpecificationBuilder<T> {

    private final List<SearchCriteria> searchCriteriaList;

    public SearchSpecificationBuilder() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public SearchSpecificationBuilder<T> with(String isAndCondition, String type, String key, String comparator, String value) {
        searchCriteriaList.add(new SearchCriteria(isAndCondition, type, key, comparator, value));
        return this;
    }

    public Specification<T> build() {
        if (searchCriteriaList.isEmpty()) {
            return null;
        }

        Iterator<SearchCriteria> iterator = searchCriteriaList.iterator();
        SearchCriteria criteria = iterator.next();

        Specification<T> specification = Specification.where(new SearchSpecification<>(criteria));
        while (iterator.hasNext()) {
            criteria = iterator.next();
            specification = criteria.isAndCondition() ?
                    specification.and(new SearchSpecification<>(criteria)) :
                    specification.or(new SearchSpecification<>(criteria));
        }
        return specification;
    }

}
