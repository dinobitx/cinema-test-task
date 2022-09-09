package com.geniusee.testtask.specification;

import com.geniusee.testtask.dao.model.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderSpecificationBuilder {

    private final List<SearchCriteria> params;

    public OrderSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public OrderSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key,operation, value));
        return this;
    }

    public Specification<Order> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Order>> specs = params.stream()
                .map(OrderSpecification::new)
                .collect(Collectors.toList());

        Specification<Order> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

}
