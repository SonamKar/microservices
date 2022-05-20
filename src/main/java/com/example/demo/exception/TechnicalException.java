package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.INTERNAL_SERVER_ERROR
)
public class TechnicalException extends BaseException{
    public TechnicalException(String code, String errorType, String errorMessageCode,
                                     String reason) {
        super(code, errorType, errorMessageCode, reason);
    }
}
