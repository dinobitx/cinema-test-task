package com.geniusee.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericApiResponse<T> {

    private T body;
    private List<ErrorInfo> errors;

    public GenericApiResponse(final T body) {
        this.body = body;
    }
}