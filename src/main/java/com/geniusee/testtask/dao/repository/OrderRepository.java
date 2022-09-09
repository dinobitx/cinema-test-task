package com.geniusee.testtask.dao.repository;

import com.geniusee.testtask.dao.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends  JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
