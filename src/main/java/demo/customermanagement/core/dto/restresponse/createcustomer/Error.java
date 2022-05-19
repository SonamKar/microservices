package demo.customermanagement.core.dto.restresponse.createcustomer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "errorCode",
        "errorSystem",
        "errorType",
        "errorCategory",
        "errorDescription",
        "errorReference"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorSystem")
    private String errorSystem;

    @JsonProperty("errorType")
    private String errorType;

    @JsonProperty("errorCategory")
    private String errorCategory;

    @JsonProperty("errorDescription")
    private String errorDescription;

    @JsonProperty("errorReference")
    private String errorReference;
}

