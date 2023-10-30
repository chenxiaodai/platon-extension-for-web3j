package network.platon.web3j.splaton.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import network.platon.web3j.platon.contracts.converter.HexString2BigIntegerConverter;

import java.math.BigInteger;

@Data
public class EpochInfo {

    /**
     * 出块奖励--废弃
     */
    @JsonProperty("PackageReward")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger packageReward;

    /**
     * 结算周期质押奖励--废弃
     */
    @JsonProperty("StakingReward")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger stakingReward;

    /**
     * 当前增发周期
     */
    @JsonProperty("YearNum")
    private Long yearNum;

    /**
     * 当前增发周期开始区块号
     */
    @JsonProperty("YearStartNum")
    private Long yearStartNum;

    /**
     * 当前增发周期结束区块号
     */
    @JsonProperty("YearEndNum")
    private Long yearEndNum;

    /**
     * 当前增发周期剩下的结算周期数
     */
    @JsonProperty("RemainEpoch")
    private Long remainEpoch;

    /**
     * 平均出块时间
     */
    @JsonProperty("AvgPackTime")
    private Long avgPackTime;

    /**
     * 当前结算周期的出块奖励
     */
    @JsonProperty("CurPackageReward")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger curPackageReward;

    /**
     * 当前结算周期的质押奖励
     */
    @JsonProperty("CurStakingReward")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger curStakingReward;

    /**
     * 下一个结算周期的出块奖励
     */
    @JsonProperty("NextPackageReward")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger nextPackageReward;

    /**
     * 下一个结算周期的质押奖励
     */
    @JsonProperty("NextStakingReward")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger nextStakingReward;
}