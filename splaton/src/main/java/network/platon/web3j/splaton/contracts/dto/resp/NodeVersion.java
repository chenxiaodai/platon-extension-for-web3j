package network.platon.web3j.splaton.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class NodeVersion {
    @JsonProperty("ProgramVersion")
    private Integer bigVersion;
    @JsonProperty("NodeId")
    private String nodeId;
}
