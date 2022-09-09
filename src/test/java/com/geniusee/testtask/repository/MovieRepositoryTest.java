package com.geniusee.testtask.repository;

import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dao.repository.MovieRepository;
import com.geniusee.testtask.specification.MovieSpecification;
import com.geniusee.testtask.specification.SearchCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.util.List;

import static com.geniusee.testtask.util.DeserializationUtil.deserialize;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository repository;
    private Movie movie_one;
    private Movie movie_two;

    @BeforeEach
    public void init() throws IOException {
        movie_one = repository.save(deserialize("templates/movie.json", Movie.class));
        movie_two = repository.save(deserialize("templates/movie_1.json", Movie.class));
    }

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    public void givenTitle_whenGettingListOfMovies_thenCorrect() {
        MovieSpecification spec =
                new MovieSpecification(new SearchCriteria("title", ":", "Night"));

        List<Movie> results = repository.findAll(spec);
        assertEquals(2, results.stream().filter(m -> m.getTitle().contains("Night")).count());
    }

    @Test
    public void givenIncorrectTitle_whenGettingListOfMovies_thenIncorrect() {
        MovieSpecification spec =
                new MovieSpecification(new SearchCriteria("title", ">", "QQQ"));

        List<Movie> results = repository.findAll(spec);
        assertTrue(results.isEmpty());
    }

    @Test
    public void givenFilter_whenGettingListOfMovies_thenCorrect() {
        MovieSpecification spec =
                new MovieSpecification(new SearchCriteria("title", ">", "Night"));

        List<Movie> results = repository.findAll(spec);
        assertFalse(results.isEmpty());
    }

}
