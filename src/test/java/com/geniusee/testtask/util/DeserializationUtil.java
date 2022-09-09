package com.geniusee.testtask.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class DeserializationUtil {

    public static <T> T deserialize(String pathToFile, Class<T> tClass) throws IOException {

        var objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);

        var resourceAsStream = DeserializationUtil.class.getClassLoader().getResourceAsStream(pathToFile);

        return objectMapper.readValue(resourceAsStream, tClass);
    }

    public static String deserialize(String pathToFile) throws IOException {
        var resourceAsStream = DeserializationUtil.class.getClassLoader().getResourceAsStream(pathToFile);
        return new String(requireNonNull(resourceAsStream).readAllBytes());
    }
}