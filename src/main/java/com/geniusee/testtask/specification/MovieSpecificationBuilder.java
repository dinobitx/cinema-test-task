package com.geniusee.testtask.specification;

import com.geniusee.testtask.dao.model.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSpecificationBuilder {

    private final List<SearchCriteria> params;

    public MovieSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public MovieSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key,operation, value));
        return this;
    }

    public Specification<Movie> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Movie>> specs = params.stream()
                .map(MovieSpecification::new)
                .collect(Collectors.toList());

        Specification<Movie> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

}
