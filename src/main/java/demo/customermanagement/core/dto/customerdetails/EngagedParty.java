package demo.customermanagement.core.dto.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.List;

@JsonPropertyOrder({
    "@referredType",
    "givenName",
    "middleName",
    "familyName",
    "fullName",
    "gender",
    "nationality",
    "birthDate",
    "contactMedium",
    "individualIdentification",
    "partyCharacteristic"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EngagedParty {

  @JsonProperty("@referredType")
  public String referredType;

  @JsonProperty("givenName")
  public String givenName;

  @JsonProperty("middleName")
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

  @JsonProperty("partyCharacteristic")
  public List<PartyCharacteristic> partyCharacteristic;
}
