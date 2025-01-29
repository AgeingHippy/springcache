package com.ageinghippy.springcache.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// return the 404 NOT FOUND status when this exception is thrown
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    // pass the incoming message to the parent RuntimeException
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
