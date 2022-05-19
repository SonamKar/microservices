package demo.customermanagement.core.dto.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
    "mediumType",
    "characteristic"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactMedium {

  @JsonProperty("mediumType")
  public String mediumType;

  @JsonProperty("characteristic")
  public MediumCharacteristic characteristic;
}
