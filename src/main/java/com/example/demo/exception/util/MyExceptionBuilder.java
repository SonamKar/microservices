package com.example.demo.exception.util;

import com.example.demo.exception.AuthorizationException;
import com.example.demo.exception.FunctionalException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.TechnicalException;
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
  public void throwTechnicalException(String code, String errorType, String errorMessageCode,
                                             String reason){
    throw new TechnicalException(code, errorType, errorMessageCode, reason);
  }
  public void throwFunctionalException(String code, String errorType, String errorMessageCode,
                                             String reason){
    throw new FunctionalException(code, errorType, errorMessageCode, reason);
  }
}
