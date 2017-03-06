package hr.eestec_zg.frmsbackend.controllers;

import hr.eestec_zg.frmsbackend.exceptions.CompanyNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.EventNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.TaskNotFoundException;
import hr.eestec_zg.frmsbackend.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = {BadCredentialsException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleAccessException() {
        return Collections.singletonMap("status", "Access denied");
    }

    @ExceptionHandler(value = {UserNotFoundException.class, CompanyNotFoundException.class,
            EventNotFoundException.class, TaskNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleResourceNotFoundException() {
        return Collections.singletonMap("status", "Resource not found");
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(IllegalArgumentException e) {
        return Collections.singletonMap("status", e.getMessage());
    }

}
