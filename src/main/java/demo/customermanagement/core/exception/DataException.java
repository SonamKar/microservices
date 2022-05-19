package demo.customermanagement.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.NOT_ACCEPTABLE
)
public class DataException extends BaseException{
    public DataException(String code, String errorType, String errorMessageCode,
                              String reason) {
        super(code, errorType, errorMessageCode, reason);
    }
}
