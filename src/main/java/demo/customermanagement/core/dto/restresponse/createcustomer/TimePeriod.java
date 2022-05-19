package demo.customermanagement.core.dto.restresponse.createcustomer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TimePeriod {
    @JsonProperty("startDateTime")
    public String startDateTime;

    @JsonProperty("endDateTime")
    public String endDateTime;
}
