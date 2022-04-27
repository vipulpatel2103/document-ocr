package com.doc.ocr.rest;

import com.doc.ocr.dto.StandardResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        return new ResponseEntity<>(StandardResponse.builder()
                .errors(new String[]{ex.getMessage()})
                .build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleExceptionV2(
            Exception ex, WebRequest request) {

        return new ResponseEntity<>(StandardResponse.builder()
                .errors((String[]) Stream.of(ex.getMessage()).toArray())
                .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(StandardResponse.builder()
                .errors((String[]) errors.toArray())
                .build()
                , HttpStatus.BAD_REQUEST);
    }
}
