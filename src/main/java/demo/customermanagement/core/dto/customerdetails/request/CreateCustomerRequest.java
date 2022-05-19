package demo.customermanagement.core.dto.customerdetails.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import demo.customermanagement.core.dto.customerdetails.EngagedParty;

@JsonPropertyOrder({
        "name",
        "engagedParty"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

    @JsonProperty("name")
    public String name;

    @JsonProperty("engagedParty")
    public EngagedParty engagedParty;
}
