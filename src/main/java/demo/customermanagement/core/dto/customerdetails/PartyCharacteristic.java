package demo.customermanagement.core.dto.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
    "name",
    "value",
    "valueType"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartyCharacteristic {

  @JsonProperty("name")
  public String name;

  @JsonProperty("value")
  public String value;

  @JsonProperty("valueType")
  public String valueType;
}
