package com.geniusee.testtask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dao.model.Order;
import com.geniusee.testtask.dao.model.OrderToMovie;
import com.geniusee.testtask.dao.repository.MovieRepository;
import com.geniusee.testtask.dao.repository.OrderRepository;
import com.geniusee.testtask.dao.repository.OrderToMovieRepository;
import com.geniusee.testtask.dto.CreateOrderRequest;
import com.geniusee.testtask.dto.MovieForOrder;
import com.geniusee.testtask.dto.OrderDTO;
import com.geniusee.testtask.dto.UpdateOrderRequest;
import com.geniusee.testtask.exception.ResourceNotFoundException;
import com.geniusee.testtask.specification.OrderSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MovieRepository movieRepository;
    private final OrderToMovieRepository orderToMovieRepository;
    private final ObjectMapper mapper;

    public Page<OrderDTO> findAll(final String search,
                                  final Pageable pageable) {
        OrderSpecificationBuilder builder = new OrderSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\S+?);");
        Matcher matcher = pattern.matcher(search + ";");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Order> spec = builder.build();
        Page<Order> allOrders = orderRepository.findAll(spec, pageable);
        List<Long> orderIdList = allOrders.stream().map(Order::getId).collect(Collectors.toList());
        List<OrderToMovie> allById = orderToMovieRepository.findAllById(orderIdList);
        List<OrderDTO> response = allOrders.stream()
                .map(o -> mapper.convertValue(o, OrderDTO.class))
                .peek(mo -> {
                    List<Movie> movies = allById.stream()
                            .filter(i -> i.getOrder().getId().equals(mo.getId()))
                            .map(OrderToMovie::getMovie)
                            .collect(Collectors.toList());
                    mo.setMovies(movies);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(response, pageable, orderIdList.size());
    }

    public OrderDTO findById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Order.class, "id", orderId));
        OrderDTO response = mapper.convertValue(order, OrderDTO.class);
        List<Movie> movies = orderToMovieRepository.findByOrderId(orderId).stream()
                .map(OrderToMovie::getMovie)
                .collect(Collectors.toList());
        response.setMovies(movies);
        return response;
    }

    public OrderDTO create(final CreateOrderRequest request) {
        Order order = mapper.convertValue(request, Order.class);
        order.setCreatedAt(LocalDateTime.now());
        Order save = orderRepository.save(order);
        List<Movie> orderMovies = saveOrderToMovies(request.getMovieForOrders(), save);
        OrderDTO response = mapper.convertValue(order, OrderDTO.class);
        response.setMovies(orderMovies);
        return response;
    }

    public OrderDTO update(final Long orderId,
                           final UpdateOrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Order.class, "id", orderId));
        orderToMovieRepository.deleteByOrderId(orderId);
        order.setClientName(request.getClientName());
        order.setStaffName(request.getStaffName());
        orderRepository.save(order);
        List<Movie> orderMovies = saveOrderToMovies(request.getMovieForOrders(), order);
        OrderDTO response = mapper.convertValue(order, OrderDTO.class);
        response.setMovies(orderMovies);
        return response;
    }

    private List<Movie> saveOrderToMovies(List<MovieForOrder> request, Order save) {
        List<Movie> allMoviesById = movieRepository.findAllById(request.stream()
                .map(MovieForOrder::getMovieId)
                .collect(Collectors.toList()));
        List<OrderToMovie> orderToMovies = request.stream()
                .map(i -> {
                    Movie movie = allMoviesById.stream()
                            .filter(m -> m.getId().equals(i.getMovieId()))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException(Movie.class, "id", i.getMovieId()));
                    return OrderToMovie.builder()
                            .order(save)
                            .movie(movie)
                            .quantity(i.getQuantity())
                            .build();
                }).collect(Collectors.toList());
        orderToMovieRepository.saveAll(orderToMovies);
        return allMoviesById;
    }

    @Transactional
    public void delete(final Long orderId) {
        orderToMovieRepository.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }
}
