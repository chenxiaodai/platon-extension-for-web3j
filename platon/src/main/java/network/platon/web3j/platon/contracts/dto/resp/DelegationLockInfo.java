package network.platon.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import network.platon.web3j.platon.contracts.converter.HexString2BigIntegerConverter;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class DelegationLockInfo {

    /**
     * 处于解锁期的委托金,待用户领取后返回到用户余额
     */
    @JsonProperty("Released")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger released;
    /**
     * 处于解锁期的委托金,待用户领取后返回到用户锁仓账户
     */
    @JsonProperty("RestrictingPlan")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger restrictingPlan;

    /**
     * 处于锁定期的委托金
     */
    @JsonProperty("Locks")
    private List<DelegationLockItem> locks;


    @Data
    public static class DelegationLockItem {
        /**
         * 解锁的周期
         */
        @JsonProperty("Epoch")
        private BigInteger epoch;
        /**
         * 锁定的金额,自由账户
         */
        @JsonProperty("Released")
        @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
        private BigInteger released;
        /**
         * 锁定的金额,锁仓账户
         */
        @JsonProperty("RestrictingPlan")
        @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
        private BigInteger restrictingPlan;
    }
}
