package demo.customermanagement.core.dto.restresponse.createcustomer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class RelatedParty {
    @JsonProperty("givenName")
    public String givenName;

    @JsonProperty("givenName")
    public String middleName;

    @JsonProperty("familyName")
    public String familyName;

    @JsonProperty("fullName")
    public String fullName;

    @JsonProperty("gender")
    public String gender;

    @JsonProperty("nationality")
    public String nationality;

    @JsonProperty("birthDate")
    public String birthDate;

    @JsonProperty("contactMedium")
    public List<ContactMedium> contactMedium;

    @JsonProperty("individualIdentification")
    public List<IndividualIdentification> individualIdentification;

    @JsonProperty("individualIdentification")
    public List<Characteristic> partyCharacteristic;


}
