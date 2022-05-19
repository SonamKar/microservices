package demo.customermanagement.core.dto.customerdetails.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "id",
        "@referredType"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @JsonProperty("id")
    public String id;

    @JsonProperty("@referredType")
    public String referredType;

}
