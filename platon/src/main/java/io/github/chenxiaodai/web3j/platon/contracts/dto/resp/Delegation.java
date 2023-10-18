package io.github.chenxiaodai.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.chenxiaodai.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import io.github.chenxiaodai.web3j.platon.contracts.converter.HexString2BigIntegerConverter;
import lombok.Data;

import java.math.BigInteger;
@Data
public class Delegation {

    /**
     * 委托人的账户地址
     */
    @JsonProperty("Addr")
    @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
    private String delegateAddress;
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
    /**
     * 最近一次对该候选人发起的委托时的结算周期
     */
    @JsonProperty("DelegateEpoch")
    private BigInteger delegateEpoch;
    /**
     * 发起委托账户的自由金额的锁定期委托的von
     */
    @JsonProperty("Released")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateReleased;
    /**
     * 发起委托账户的自由金额的犹豫期委托的von
     */
    @JsonProperty("ReleasedHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateReleasedHes;
    /**
     * 发起委托账户的锁仓金额的锁定期委托的von
     */
    @JsonProperty("RestrictingPlan")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateLocked;
    /**
     * 发起委托账户的锁仓金额的犹豫期委托的von
     */
    @JsonProperty("RestrictingPlanHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateLockedHes;
    /**
     * 待领取的委托收益von
     */
    @JsonProperty("CumulativeIncome")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger cumulativeIncome;
    /**
     * 犹豫期的委托金,来自锁定期,源自自由金额
     */
    @JsonProperty("LockReleasedHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger lockReleasedHes;
    /**
     * 犹豫期的委托金,来自锁定期,源自锁仓金额
     */
    @JsonProperty("LockRestrictingPlanHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger lockRestrictingPlanHes;
}
