package demo.customermanagement.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Customer_Create {

    @JsonProperty("name")
    public String name;

    @JsonProperty("href")
    public RelatedParty engagedParty;

}
