package io.github.chenxiaodai.web3j.platon.contracts.dto.resp;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.chenxiaodai.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import lombok.Data;

import java.math.BigInteger;

@Data
public class DelegationIdInfo {

    /**
     * 验证人节点的地址
     */
    @JsonProperty("Addr")
    @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
    private String address;
    /**
     * 验证人的节点Id
     */
    @JsonProperty("NodeId")
    private String nodeId;
    /**
     * 发起质押时的区块高度
     */
    @JsonProperty("StakingBlockNum")
    private BigInteger stakingBlockNum;
}
