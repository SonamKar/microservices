package demo.customermanagement.core.dto.customerdetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


@JsonPropertyOrder({
        "startDateTime",
        "endDateTime"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidFor {

    @JsonProperty("startDateTime")
    public String startDateTime;

    @JsonProperty("endDateTime")
    public String endDateTime;
}