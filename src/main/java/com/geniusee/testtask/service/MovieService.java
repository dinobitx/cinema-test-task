package com.geniusee.testtask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dao.repository.MovieRepository;
import com.geniusee.testtask.dto.CreateMovieRequest;
import com.geniusee.testtask.dto.MovieStatus;
import com.geniusee.testtask.dto.UpdateMovieRequest;
import com.geniusee.testtask.exception.IncorrectMovieReleaseException;
import com.geniusee.testtask.exception.ResourceNotFoundException;
import com.geniusee.testtask.specification.MovieSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ObjectMapper mapper;

    public Page<Movie> findAll(final String search, final Pageable pageable) {
        MovieSpecificationBuilder builder = new MovieSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\S+?);");
        Matcher matcher = pattern.matcher(search + ";");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Movie> spec = builder.build();
        return movieRepository.findAll(spec, pageable);
    }

    public Movie findById(final Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Movie.class, "id", id));
    }

    public Movie create(final CreateMovieRequest request) {
        Movie movie = mapper.convertValue(request, Movie.class);
        resolveMovieStatus(movie);
        return movieRepository.save(movie);
    }

    private void resolveMovieStatus(final Movie movie) {
        LocalDateTime releaseStart = movie.getReleaseStart();
        LocalDateTime releaseEnd = movie.getReleaseEnd();
        LocalDateTime today = LocalDateTime.now();
        if (releaseStart.isAfter(today)
                && releaseStart.isBefore(releaseEnd)) {
            movie.setStatus(MovieStatus.WAIT_TO_RELEASE.toString());
        } else if ((releaseStart.isBefore(today) || releaseStart.isEqual(today))
                && releaseStart.isBefore(releaseEnd)) {
            movie.setStatus(MovieStatus.IN_RELEASE.toString());
        } else if (releaseStart.isBefore(today)
                && releaseEnd.isBefore(today)
                && releaseStart.isBefore(releaseEnd)) {
            movie.setStatus(MovieStatus.OUT_OF_RELEASE.toString());
        } else {
            throw new IncorrectMovieReleaseException();
        }
    }

    public Movie update(final Long movieId, final UpdateMovieRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(Movie.class, "id", movieId));
        movie.setTitle(request.getTitle());
        movie.setDuration(request.getDuration());
        movie.setCost(request.getCost());
        movie.setRank(request.getRank());
        movie.setReleaseStart(request.getReleaseStart());
        movie.setReleaseEnd(request.getReleaseEnd());
        movie.setDescription(request.getDescription());
        resolveMovieStatus(movie);
        return movieRepository.save(movie);
    }

    public void delete(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
