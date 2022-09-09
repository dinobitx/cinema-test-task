package com.geniusee.testtask.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieRequest {
    private String title;
    private String description;
    private Integer duration;
    private BigDecimal cost;
    private BigDecimal rank;
    @ApiParam(defaultValue = "2022-09-09T00:00")
    private LocalDateTime releaseStart;
    @ApiParam(defaultValue = "2022-09-10T23:59")
    private LocalDateTime releaseEnd;
}
