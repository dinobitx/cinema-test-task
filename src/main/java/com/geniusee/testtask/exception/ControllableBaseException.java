package com.geniusee.testtask.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
public class ControllableBaseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String code;

    public ControllableBaseException(final String message,
                                     final HttpStatus httpStatus,
                                     final String code,
                                     final Exception exception) {
        super(message, exception);
        this.httpStatus = Objects.requireNonNull(httpStatus);
        this.code = Objects.requireNonNull(code);
    }

    public ControllableBaseException(final String message,
                                     final HttpStatus httpStatus,
                                     final String code) {
        super(message);
        this.httpStatus = Objects.requireNonNull(httpStatus);
        this.code = Objects.requireNonNull(code);
    }
}
