package com.geniusee.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {
    private String title;
    private String description;
    private Integer duration;
    private BigDecimal cost;
    private BigDecimal rank;
    private LocalDateTime releaseStart;
    private LocalDateTime releaseEnd;
}
