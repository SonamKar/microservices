package com.example.additionalmovieinfo.exception.util;

import com.example.additionalmovieinfo.exception.AuthorizationException;
import com.example.additionalmovieinfo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyExceptionBuilder {
  public MyExceptionBuilder(){

  }
  public void throwAuthorizationException(String code, String errorType, String errorMessageCode,
      String reason){
    throw new AuthorizationException(code, errorType, errorMessageCode, reason);
  }
  public void throwResourceNotFoundException(String code, String errorType, String errorMessageCode,
      String reason){
    throw new ResourceNotFoundException(code, errorType, errorMessageCode, reason);
  }
}
