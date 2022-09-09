package com.geniusee.testtask.dao.repository;

import com.geniusee.testtask.dao.model.OrderToMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderToMovieRepository extends JpaRepository<OrderToMovie, Long>, JpaSpecificationExecutor<OrderToMovie> {
    void deleteByOrderId(Long orderId);
    List<OrderToMovie> findByOrderId(Long orderId);
}
