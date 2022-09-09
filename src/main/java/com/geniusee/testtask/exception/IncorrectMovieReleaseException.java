package com.geniusee.testtask.exception;

import org.springframework.http.HttpStatus;

public class IncorrectMovieReleaseException extends ControllableBaseException {
    private static final String MESSAGE_TEMPLATE = "Incorrect movie release date";
    private static final String STATUS_CODE = "INCORRECT_RELEASE_DATE";

    public IncorrectMovieReleaseException() {
        super(MESSAGE_TEMPLATE, HttpStatus.BAD_REQUEST, STATUS_CODE);
    }
}
