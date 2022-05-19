package demo.customermanagement.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.FORBIDDEN
)
public class AuthorizationException extends BaseException{

  public AuthorizationException(String code, String errorType, String errorMessageCode,
      String reason) {
    super(code, errorType, errorMessageCode, reason);
  }
}
