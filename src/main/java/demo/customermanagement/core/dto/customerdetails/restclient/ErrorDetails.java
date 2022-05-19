package demo.customermanagement.core.dto.customerdetails.restclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "description",
        "reference"


})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("reference")
    private String reference;


}

