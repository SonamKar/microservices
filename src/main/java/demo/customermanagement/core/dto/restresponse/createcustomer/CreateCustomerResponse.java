package demo.customermanagement.core.dto.restresponse.createcustomer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerResponse {
    @JsonProperty("header")
    public Header header;

    @JsonProperty("body")
    public Body body;

    @JsonProperty("status")
    public Status status;
}
