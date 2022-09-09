package com.geniusee.testtask.exception;

import com.geniusee.testtask.dto.ErrorInfo;
import com.geniusee.testtask.dto.GenericApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestExceptionsHandler {

    @ExceptionHandler(value = {ControllableBaseException.class})
    public ResponseEntity<GenericApiResponse<?>> catchInternalErrors(ControllableBaseException e, WebRequest request) {
        log.error("Sent exception to UI, e = {}, request = {}", e, request);

        GenericApiResponse<?> genericApiResponse = GenericApiResponse.builder()
                .errors(
                        Collections.singletonList(
                                new ErrorInfo(e.getCode(), e.getMessage())))
                .build();
        return new ResponseEntity<>(genericApiResponse, e.getHttpStatus());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<GenericApiResponse<?>> catchAllErrors(Exception e, WebRequest request) {
        log.error(e.getMessage());
        return catchInternalErrors(
                new ControllableBaseException(
                        String.format("Error: %s", e.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "INTERNAL_SERVER_ERROR",
                        e),
                request);
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResponseEntity<GenericApiResponse<?>> catchValidationErrors(MethodArgumentNotValidException e, WebRequest request) {
//        List<ErrorInfo> errorInfos = e.getBindingResult().getAllErrors().stream()
//                .map(error -> {
//                    log.error("Sent exception to UI, e = {}, request = {}", error.getDefaultMessage(), request);
//                    return new ErrorInfo("BAD_REQUEST", error.getDefaultMessage());
//                })
//                .collect(Collectors.toList());
//        GenericApiResponse<?> genericApiResponse = GenericApiResponse.builder()
//                .errors(errorInfos)
//                .build();
//        return new ResponseEntity<>(genericApiResponse, HttpStatus.BAD_REQUEST);
//    }
}
