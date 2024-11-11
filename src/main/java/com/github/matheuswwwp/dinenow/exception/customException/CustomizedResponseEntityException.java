package com.github.matheuswwwp.dinenow.exception.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomizedResponseEntityException extends RuntimeException {
    private static final long serialVersionID = 1L;
    public CustomizedResponseEntityException(String ex) {
        super(ex);
    }
}
