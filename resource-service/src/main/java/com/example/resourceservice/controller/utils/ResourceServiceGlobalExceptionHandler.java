package com.example.resourceservice.controller.utils;

import com.example.resourceservice.dto.RESTExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

@RequestMapping
@ResponseBody
@ControllerAdvice
public class ResourceServiceGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
              HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(processStatusException(ex, httpStatus), httpStatus);
    }

    @ExceptionHandler({ ConstraintViolationException.class, UnsupportedOperationException.class } )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<RESTExceptionDTO> handleBadRequestStatusException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(processStatusException(ex, httpStatus), httpStatus);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<RESTExceptionDTO> handleNotFoundStatusException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(processStatusException(ex, httpStatus), httpStatus);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RESTExceptionDTO> handleInternalServerStatusException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(processStatusException(ex, httpStatus), httpStatus);
    }

    private RESTExceptionDTO processStatusException(Exception ex, HttpStatus httpStatus) {
        final String errorMessage = ex instanceof HandlerMethodValidationException ?
                retrieveMethodValidationErrorMessage((HandlerMethodValidationException) ex)
                : Optional.of(getRootCauseMessage(ex)).orElse(ex.toString());
        return new RESTExceptionDTO(httpStatus.getReasonPhrase(), httpStatus.value(), errorMessage);
    }

    private String retrieveMethodValidationErrorMessage(HandlerMethodValidationException ex) {
        return ex.getAllValidationResults().stream()
                .map(ParameterValidationResult::getResolvableErrors)
                .flatMap(Collection::stream)
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
    }
}
