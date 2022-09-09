package com.geniusee.testtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private String clientName;
    private String staffName;
    private List<MovieForOrder> movieForOrders;
}
