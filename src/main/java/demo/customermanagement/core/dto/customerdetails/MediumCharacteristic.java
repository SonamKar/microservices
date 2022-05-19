package demo.customermanagement.core.dto.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediumCharacteristic {

  @JsonProperty("emailAddress")
  public String emailAddress;

  @JsonProperty("phoneNumber")
  public String phoneNumber;
}
