package demo.customermanagement.core.dto.response.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ContactMedium {

    @JsonProperty("characteristic")
    public MediumCharacteristic characteristic;

    @JsonProperty("mediumType")
    public String mediumType;
}
