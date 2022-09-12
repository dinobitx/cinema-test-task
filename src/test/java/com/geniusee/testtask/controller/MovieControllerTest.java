package com.geniusee.testtask.controller;

import com.geniusee.testtask.dao.model.Movie;
import com.geniusee.testtask.dao.repository.MovieRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static com.geniusee.testtask.util.DeserializationUtil.deserialize;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MovieControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private MovieRepository repository;
    @BeforeEach
    public void setup() throws IOException {
        RestAssured.port = port;
        repository.save(deserialize("templates/movie.json", Movie.class));
        repository.save(deserialize("templates/movie_1.json", Movie.class));
    }

    @Test
    public void findAllTest() {
        when().get("api/v1/movies?search=title:Night").then().statusCode(200).assertThat()
                .body("body.content.cost", hasItems(12.99f, 14.99f));
    }

    @Test
    public void findByIdTest() {
        System.out.println(given().get("api/v1/movies/{movieId}", 2).then().extract().asString());
        when().get("api/v1/movies/{movieId}", 2).then().statusCode(200).assertThat()
                .body("body.cost", equalTo(14.99f));
    }
}
