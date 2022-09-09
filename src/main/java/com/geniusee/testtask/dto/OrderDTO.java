package com.geniusee.testtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dao.model.OrderToMovie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private List<Movie> movies;
    private String clientName;
    private String staffName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
