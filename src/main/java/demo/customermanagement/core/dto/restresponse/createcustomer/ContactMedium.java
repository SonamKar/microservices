package demo.customermanagement.core.dto.restresponse.createcustomer;

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
