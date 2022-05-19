package com.example.restapiexample.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaseException extends RuntimeException{
  protected String code;
  protected String errorType;
  protected String errorMessageCode;
  protected String reason;

  public BaseException(String code, String errorType, String errorMessageCode,
      String reason) {
    this.code = code;
    this.errorType = errorType;
    this.errorMessageCode = errorMessageCode;
    this.reason = reason;
  }
}
