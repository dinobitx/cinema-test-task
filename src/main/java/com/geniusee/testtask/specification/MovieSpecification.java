package com.geniusee.testtask.specification;

import com.geniusee.testtask.dao.model.Movie;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class MovieSpecification implements Specification<Movie> {
    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getFilter().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getFieldName()), criteria.getFieldValue().toString());
        } else if (criteria.getFilter().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.get(criteria.getFieldName()), criteria.getFieldValue().toString());
        } else if (criteria.getFilter().equalsIgnoreCase(":")) {
            if (root.get(criteria.getFieldName()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getFieldName()), "%" + criteria.getFieldValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getFieldName()), criteria.getFieldValue());
            }
        }
        return null;
    }
}
