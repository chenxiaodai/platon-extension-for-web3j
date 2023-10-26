package network.platon.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import network.platon.web3j.platon.contracts.converter.HexString2BigIntegerConverter;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class RestrictingItem {
    /**
     * 锁仓余额
     */
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger balance;
    /**
     * 质押/抵押金额
     */
    @JsonProperty("Pledge")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger pledge;
    /**
     * 欠释放金额
     */
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger debt;
    /**
     * 锁仓分录信息
     */
    @JsonProperty("plans")
    private List<RestrictingInfo> info;


    @Data
    public static class RestrictingInfo {

        /**
         * 释放区块高度
         */
        private BigInteger blockNumber;
        /**
         * 释放金额
         */
        @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
        private BigInteger amount;
    }

}
