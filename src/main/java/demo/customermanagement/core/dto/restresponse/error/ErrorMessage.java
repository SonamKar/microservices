package demo.customermanagement.core.dto.restresponse.error;

import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage implements Serializable {

    private String code;
    private String reason;
    private String message;
    private String status;
}
