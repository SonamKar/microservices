package demo.customermanagement.core.dto.restrequest.createcustomer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "systemId",
        "serverInfo",
        "messageId",
        "timestamp",
        "domainId",
        "serviceId",
        "operationType"
})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Header {

    @JsonProperty("systemId")
    private String systemId;

    @JsonProperty("serverInfo")
    private String serverInfo;

    @JsonProperty("messageId")
    private String messageId;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("domainId")
    private String domainId;

    @JsonProperty("serviceId")
    private String serviceId;

    @JsonProperty("operationType")
    private String operationType;
}
