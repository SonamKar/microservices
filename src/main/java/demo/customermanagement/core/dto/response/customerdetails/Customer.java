package demo.customermanagement.core.dto.response.customerdetails;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import demo.customermanagement.core.dto.request.RelatedParty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@Builder
public class Customer {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("engagedParty")
    public RelatedParty engagedParty;

    @JsonProperty("account")
    public List<AccountRef> account;
}
