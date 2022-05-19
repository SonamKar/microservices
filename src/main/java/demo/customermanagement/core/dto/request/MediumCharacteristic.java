package demo.customermanagement.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MediumCharacteristic {
    @JsonProperty("emailAddress")
    public String emailAddress;

    @JsonProperty("phoneNumber")
    public String phoneNumber;
}
