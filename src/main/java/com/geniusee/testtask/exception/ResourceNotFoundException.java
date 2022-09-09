package com.geniusee.testtask.exception;

import org.springframework.http.HttpStatus;

import static java.lang.String.format;

public class ResourceNotFoundException extends ControllableBaseException {

    private static final String MESSAGE_TEMPLATE = "%s with identifier %s not found";
    private static final String MESSAGE_WITH_ID_NAME_TEMPLATE = "%s with %s %s not found";
    private static final String STATUS_CODE = "RESOURCE_NOT_FOUND";

    public ResourceNotFoundException(final Class<?> type, final Object identifier) {
        super(format(MESSAGE_TEMPLATE, type.getSimpleName(), identifier), HttpStatus.BAD_REQUEST, STATUS_CODE);
    }

    public ResourceNotFoundException(final String resourceName, final Object identifier) {
        super(format(MESSAGE_TEMPLATE, resourceName, identifier), HttpStatus.BAD_REQUEST, STATUS_CODE);
    }

    public ResourceNotFoundException(final Class<?> type, final String identifierName, final Object identifierValue) {
        super(format(MESSAGE_WITH_ID_NAME_TEMPLATE, type.getSimpleName(), identifierName, identifierValue),
                HttpStatus.BAD_REQUEST,
                STATUS_CODE);
    }
}
