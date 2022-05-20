package com.example.workflow.exception.util;

import com.example.workflow.exception.AuthorizationException;
import com.example.workflow.exception.FunctionalException;
import com.example.workflow.exception.ResourceNotFoundException;
import com.example.workflow.exception.TechnicalException;
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
