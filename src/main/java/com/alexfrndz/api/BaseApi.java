package com.alexfrndz.api;

import com.alexfrndz.pojo.exception.DuplicateEntryException;
import com.alexfrndz.pojo.exception.NotFoundException;
import com.alexfrndz.pojo.response.handlers.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class BaseApi {

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Exception> handleNoResultException(
            NoResultException nre) {
        log.error("> handleNoResultException");
        log.error("- NoResultException: ", nre);
        log.error("< handleNoResultException");
        return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        log.error("> handleException");
        log.error("- Exception: ", e);
        log.error("< handleException");
        return new ResponseEntity<Exception>(e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationFromAnnotation(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        FieldError error = result.getFieldError();
        return new ErrorResponse(error.getField(), error.getDefaultMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundError(HttpServletRequest req, NotFoundException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(HttpMessageNotReadableException exception) {
        return new ErrorResponse("INVALID_VALUE", exception.getMostSpecificCause().getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(MethodArgumentTypeMismatchException exception) {
        return new ErrorResponse("INVALID_VALUE", exception.getMostSpecificCause().getLocalizedMessage());
    }

    @ExceptionHandler(DuplicateEntryException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleDuplicate(HttpServletRequest req, DuplicateEntryException exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
    }

}

