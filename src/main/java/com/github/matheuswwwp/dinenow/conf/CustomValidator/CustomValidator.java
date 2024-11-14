package com.github.matheuswwwp.dinenow.conf.CustomValidator;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

public class CustomValidator {
    public CustomValidator() {}
    public RestResponse getMessage(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new RestResponse("alguns campos são inválidos", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, errors);
    }
}
