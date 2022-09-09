package com.geniusee.testtask.dao.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_order_to_movie", schema = "public", catalog = "cinema_db")
public class OrderToMovie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Movie movie;
    @Basic
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
