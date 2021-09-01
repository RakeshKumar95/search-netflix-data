package com.assessment.search.repositories;

import com.assessment.search.dtos.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import static com.assessment.search.util.SearchConstants.*;
import static com.assessment.search.util.SearchUtil.parse;
import static java.lang.String.format;

@NoArgsConstructor
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (criteria.getType()) {
            case NUM_PARAM:
                switch (criteria.getComparator()) {
                    case G_T:
                        return criteriaBuilder.gt(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue()));
                    case G_T_EQUALS:
                        return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue()));
                    case L_T:
                        return criteriaBuilder.lessThan(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue()));
                    case L_T_EQUALS:
                        return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue()));
                    case EQUALS:
                        return criteriaBuilder.equal(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue()));
                    case NOT_EQUALS:
                        return criteriaBuilder.notEqual(root.get(criteria.getKey()), Integer.valueOf(criteria.getValue()));
                    case IN:
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                        for (String str : criteria.getValue().split(COMMA)) {
                            in.value(Integer.valueOf(str.trim()));
                        }
                        return in;
                }
            case STRING_PARAM:
                switch (criteria.getComparator()) {
                    case EQUALS:
                        return criteriaBuilder.equal(root.get(criteria.getKey()), format(LIKE_PRE_POST, criteria.getValue()));
                    case NOT_EQUALS:
                        return criteriaBuilder.notEqual(root.get(criteria.getKey()), format(LIKE_PRE_POST, criteria.getValue()));
                    case LIKE:
                        return criteriaBuilder.like(root.get(criteria.getKey()), format(LIKE_PRE_POST, criteria.getValue()));
                    case NOT_LIKE:
                        return criteriaBuilder.notLike(root.get(criteria.getKey()), format(LIKE_PRE_POST, criteria.getValue()));
                    case IN:
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                        for (String str : criteria.getValue().split(COMMA)) {
                            in.value(str.trim());
                        }
                        return in;
                }
            case DATE_PARAM:
                Expression<String> expression = criteriaBuilder.function("STR_TO_DATE", String.class, root.get(criteria.getKey()), criteriaBuilder.literal("%M %e, %Y"));
                switch (criteria.getComparator()) {
                    case G_T:
                        return criteriaBuilder.greaterThan(expression, parse(criteria.getValue()));
                    case G_T_EQUALS:
                        return criteriaBuilder.greaterThanOrEqualTo(expression, parse(criteria.getValue()));
                    case L_T:
                        return criteriaBuilder.lessThan(expression, parse(criteria.getValue()));
                    case L_T_EQUALS:
                        return criteriaBuilder.lessThanOrEqualTo(expression, parse(criteria.getValue()));
                    case EQUALS:
                        return criteriaBuilder.equal(expression, parse(criteria.getValue()));
                    case NOT_EQUALS:
                        return criteriaBuilder.notEqual(expression, parse(criteria.getValue()));
                }
        }
        return criteriaBuilder.like(root.get(criteria.getKey()), format(LIKE_PRE_POST, criteria.getValue()));
    }
}
