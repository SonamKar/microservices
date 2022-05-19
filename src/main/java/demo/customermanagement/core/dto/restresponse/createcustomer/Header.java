package demo.customermanagement.core.dto.restresponse.createcustomer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "systemId",
        "serverInfo",
        "messageId",
        "conversationId",
        "timestamp",
        "domainId",
        "serviceId",
        "operationType",
        "userId",
        "integrationId"
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

    @JsonProperty("conversationId")
    private String conversationId;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("domainId")
    private String domainId;

    @JsonProperty("serviceId")
    private String serviceId;

    @JsonProperty("operationType")
    private String operationType;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("integrationId")
    private String integrationId;

}
