package com.geniusee.testtask.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_movie", schema = "public", catalog = "cinema_db")
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "title", nullable = false, length = 128)
    private String title;
    @Basic
    @Column(name = "duration", nullable = false)
    private int duration;
    @Basic
    @Column(name = "cost", nullable = false)
    private BigDecimal cost;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "rank", nullable = false)
    private BigDecimal rank;
    @Basic
    @Column(name = "status", nullable = false, length = 36)
    private String status;
    @Basic
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "release_start")
    private LocalDateTime releaseStart;
    @Basic
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "release_end")
    private LocalDateTime releaseEnd;
}
