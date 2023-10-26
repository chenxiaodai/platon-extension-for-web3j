package network.platon.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import network.platon.web3j.platon.contracts.converter.HexString2BigIntegerConverter;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Reward {

    @JsonProperty("nodeID")
    private String nodeId;
    private BigInteger stakingNum;
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger reward;
    public void setRewardBigIntegerValue(BigInteger reward) {
        this.reward = reward;
    }
}
