package com.geniusee.testtask.controller;

import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dto.CreateMovieRequest;
import com.geniusee.testtask.dto.GenericApiResponse;
import com.geniusee.testtask.dto.UpdateMovieRequest;
import com.geniusee.testtask.service.MovieService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/movies")
public class MovieController {
    private final MovieService service;

    @GetMapping
    public ResponseEntity<GenericApiResponse<Page<Movie>>> findAll(@RequestParam
                                                                   @ApiParam(example = "title:Movie;") final String search,
                                                                   final Pageable pageable) {
        final Page<Movie> response = service.findAll(search, pageable);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<GenericApiResponse<Movie>> findById(@PathVariable final Long movieId) {
        final Movie response = service.findById(movieId);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @PostMapping
    public ResponseEntity<GenericApiResponse<Movie>> create(@RequestBody final CreateMovieRequest request) {
        final Movie response = service.create(request);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<GenericApiResponse<Movie>> update(@PathVariable final Long movieId,
                                                            @RequestBody final UpdateMovieRequest request) {
        final Movie response = service.update(movieId, request);
        return ResponseEntity.ok(new GenericApiResponse<>(response));
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<GenericApiResponse<Void>> delete(@PathVariable final Long movieId) {
        service.delete(movieId);
        return ResponseEntity.ok(new GenericApiResponse<>());
    }

}
