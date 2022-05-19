package demo.customermanagement.core.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import demo.customermanagement.core.exception.BaseException;
import demo.customermanagement.core.exception.dto.ErrorDetail;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorDetail> handleBaseException(
      final BaseException exception) {
    exception.printStackTrace();

    final ResponseStatus status = exception.getClass().getAnnotation(ResponseStatus.class);
    final ErrorDetail error = ErrorDetail.builder()
        .code(exception.getCode())
        .errorMessageCode(exception.getErrorMessageCode())
        .errorType(exception.getErrorType())
        .reason(exception.getReason())
        .build();

    return new ResponseEntity<>(error, generateResponseHeaders(), status.code());
  }
  private MultiValueMap<String, String> generateResponseHeaders() {
    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders
        .set("content-type","application/json");
    return responseHeaders;
  }
}
