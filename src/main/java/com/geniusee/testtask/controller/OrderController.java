package com.geniusee.testtask.controller;

import com.geniusee.testtask.dto.CreateOrderRequest;
import com.geniusee.testtask.dto.GenericApiResponse;
import com.geniusee.testtask.dto.OrderDTO;
import com.geniusee.testtask.dto.UpdateOrderRequest;
import com.geniusee.testtask.service.OrderService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService service;

    @GetMapping
    public ResponseEntity<GenericApiResponse<Page<OrderDTO>>> findAll(@RequestParam
                                                                   @ApiParam(example = "title:Order;") final String search,
                                                                   final Pageable pageable) {
        final Page<OrderDTO> response = service.findAll(search, pageable);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GenericApiResponse<OrderDTO>> findById(@PathVariable final Long orderId) {
        final OrderDTO response = service.findById(orderId);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @PostMapping
    public ResponseEntity<GenericApiResponse<OrderDTO>> create(@RequestBody final CreateOrderRequest request) {
        final OrderDTO response = service.create(request);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<GenericApiResponse<OrderDTO>> update(@PathVariable final Long orderId,
                                                               @RequestBody final UpdateOrderRequest request) {
        final OrderDTO response = service.update(orderId, request);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<GenericApiResponse<Void>> delete(@PathVariable final Long orderId) {
        service.delete(orderId);
        return ResponseEntity.ok(new GenericApiResponse<>());
    }

}
