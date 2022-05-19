package demo.customermanagement.core.exception.util;

import demo.customermanagement.core.exception.AuthorizationException;
import demo.customermanagement.core.exception.DataException;
import demo.customermanagement.core.exception.ResourceNotFoundException;
import demo.customermanagement.core.exception.TechnicalException;
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
  public void throwDataException(String code, String errorType, String errorMessageCode,
                                             String reason){
    throw new DataException(code, errorType, errorMessageCode, reason) {
    };
  }
}
