package org.benevity.server.controller.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.benevity.server.openapi.model.ErrorResponse;
import org.benevity.server.service.exception.ImageNotValidException;
import org.benevity.server.service.exception.OperationNotAllowedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {
    private static final String LOG_EXCEPTION = "{} - {}";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return getErrorResponse(BAD_REQUEST, exception.getMessage(), getStackTrace(exception));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleOperationNotAllowedException(OperationNotAllowedException exception) {
        return getErrorResponse(UNAUTHORIZED, exception.getMessage(), getStackTrace(exception));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleBadCredentialsException(BadCredentialsException exception) {
        return getErrorResponse(UNAUTHORIZED, exception.getMessage(), getStackTrace(exception));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return getErrorResponse(UNAUTHORIZED, exception.getMessage(), getStackTrace(exception));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleImageNotValidException(ImageNotValidException exception) {
        return getErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), getStackTrace(exception));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAnyException(Exception exception) {
        return getErrorResponse(INTERNAL_SERVER_ERROR, exception.getMessage(), getStackTrace(exception));
    }

    private ErrorResponse getErrorResponse(HttpStatus status, String message, String stackTrace) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status(status.value());
        errorResponse.setTitle(status.getReasonPhrase());
        errorResponse.setDescription(message);

        log.error(LOG_EXCEPTION, toJson(errorResponse), stackTrace);
        return errorResponse;
    }

    private String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            log.error(LOG_EXCEPTION, exception.getMessage(), getStackTrace(exception));
            return object.toString();
        }
    }
}
