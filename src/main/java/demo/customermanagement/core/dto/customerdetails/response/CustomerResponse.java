package demo.customermanagement.core.dto.customerdetails.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import demo.customermanagement.core.dto.customerdetails.EngagedParty;

import java.util.List;

@JsonPropertyOrder({
        "name",
        "engagedParty",
        "account"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("engagedParty")
    public EngagedParty engagedParty;

    @JsonProperty("account")
    public List<Account> account;
}
