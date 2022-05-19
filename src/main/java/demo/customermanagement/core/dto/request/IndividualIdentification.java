package demo.customermanagement.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
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
    public TimePeriod validFor;
}
