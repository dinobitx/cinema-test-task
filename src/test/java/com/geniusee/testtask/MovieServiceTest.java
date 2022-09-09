package com.geniusee.testtask;

import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dao.repository.MovieRepository;
import com.geniusee.testtask.specification.SearchCriteria;
import com.geniusee.testtask.specification.MovieSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static com.geniusee.testtask.util.DeserializationUtil.deserialize;
//@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
public class MovieServiceTest {
    @Autowired
    private MovieRepository repository;

    @BeforeEach
    public void init() throws IOException {
        repository.save(deserialize("templates/movie.json", Movie.class));
        repository.save(deserialize("templates/movie_1.json", Movie.class));
    }
//
//    @AfterEach
//    public void cleanUp() {
//        repository.deleteAll();
//    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        MovieSpecification spec =
                new MovieSpecification(new SearchCriteria("title", ":","Night"));

        List<Movie> results = repository.findAll(spec);
        System.out.println(results);
    }

}
