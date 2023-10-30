package network.platon.web3j.splaton.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class HistoryLowRateSlash {
    // 被处罚节点Id
    @JsonProperty("NodeId")
    private String nodeId;
    // 处罚金额
    @JsonProperty("Amount")
    private BigInteger amount;
}
