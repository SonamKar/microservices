package com.example.workflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.NOT_ACCEPTABLE
)
public class FunctionalException extends BaseException{
    public FunctionalException(String code, String errorType, String errorMessageCode,
                                     String reason) {
        super(code, errorType, errorMessageCode, reason);
    }
}
