package com.geniusee.testtask.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SearchCriteria {
    private String fieldName;
//    private String fieldValue;
    private String filter;
    private Object fieldValue;
}
