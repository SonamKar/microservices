package demo.customermanagement.core.dto.restresponse.createcustomer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "result",
        "error",
        "errorList"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @JsonProperty("result")
    private String result;

    @JsonProperty("error")
    private Error error;

    @JsonProperty("errorList")
    private List<java.lang.Error> errorList;
}
