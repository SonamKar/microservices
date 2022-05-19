package demo.customermanagement.core.dto.restrequest.createcustomer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import demo.customermanagement.core.dto.request.Customer_Create;
import lombok.*;

@JsonPropertyOrder({
        "header",
        "body"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

    @JsonProperty("header")
    public Header header;

    @JsonProperty("body")
    public Customer_Create body;
}
