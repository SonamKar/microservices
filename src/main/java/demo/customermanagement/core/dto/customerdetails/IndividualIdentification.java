package demo.customermanagement.core.dto.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "identificationId",
        "identificationType",
        "issuingDate",
        "placeOfIssue",
        "issuingAuthority",
        "validFor"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualIdentification {

    @JsonProperty("identificationId")
    public String identificationId;

    @JsonProperty("identificationType")
    public String identificationType;

    @JsonProperty("issuingDate")
    public String issuingDate;

    @JsonProperty("placeOfIssue")
    public String placeOfIssue;

    @JsonProperty("issuingAuthority")
    public String issuingAuthority;

    @JsonProperty("validFor")
    public ValidFor validFor;
}
