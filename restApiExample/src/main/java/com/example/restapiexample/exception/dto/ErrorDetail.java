package com.example.restapiexample.exception.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDetail {
   String code;
   String errorType;
   String errorMessageCode;
   String reason;
}
