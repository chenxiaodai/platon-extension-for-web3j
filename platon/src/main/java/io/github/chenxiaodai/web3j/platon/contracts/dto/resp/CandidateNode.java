package io.github.chenxiaodai.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.chenxiaodai.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import io.github.chenxiaodai.web3j.platon.contracts.converter.HexString2BigIntegerConverter;
import lombok.Data;

import java.math.BigInteger;


@Data
public class CandidateNode {
    /**
     * 节点id
     */
    @JsonProperty("NodeId")
    private String nodeId;

    /**
     * 验证人bls公钥
     */
    @JsonProperty("BlsPubKey")
    private String blsPubKey;

    /**
     * 发起质押时使用的账户(后续操作质押信息只能用这个账户，撤销质押时，von会被退回该账户或者该账户的锁仓信息中)
     */
    @JsonProperty("StakingAddress")
    @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
    private String stakingAddress;

    /**
     * 用于接受出块奖励和质押奖励的收益账户
     */
    @JsonProperty("BenefitAddress")
    @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
    private String benefitAddress;

    /**
     * 当前结算周期奖励分成比例，采用BasePoint 1BP=0.01%
     */
    @JsonProperty("RewardPer")
    private BigInteger rewardPer;

    /**
     * 下一个结算周期奖励分成比例，采用BasePoint 1BP=0.01%
     */
    @JsonProperty("NextRewardPer")
    private BigInteger nextRewardPer;

    /**
     * 更改奖励分成比例比例时的结算周期数
     */
    @JsonProperty("RewardPerChangeEpoch")
    private BigInteger rewardPerChangeEpoch;

    /**
     * 发起质押时的交易索引
     */
    @JsonProperty("StakingTxIndex")
    private BigInteger stakingTxIndex;

    /**
     * 被质押节点的PlatON进程的真实版本号(获取版本号的接口由治理提供)
     */
    @JsonProperty("ProgramVersion")
    private BigInteger programVersion;

    /**
     * 候选人的状态(状态是根据uint32的32bit来放置的，可同时存在多个状态，值为多个同时存在的状态值相加
     * 0: 节点可用 (32个bit全为0)；
     * 1: 节点不可用 (只有最后一bit为1)；
     * 2： 节点零出块需要锁定但无需解除质押(只有倒数第二bit为1)；
     * 4： 节点的von不足最低质押门槛(只有倒数第三bit为1)；
     * 8：节点被举报双签(只有倒数第四bit为1));
     * 16: 节点零出块需要锁定并解除质押(倒数第五位bit为1);
     * 32: 节点主动发起撤销(只有倒数第六位bit为1)
     */
    @JsonProperty("Status")
    private BigInteger status;

    /**
     * 当前变更质押金额时的结算周期
     */
    @JsonProperty("StakingEpoch")
    private BigInteger stakingEpoch;

    /**
     * 发起质押时的区块高度
     */
    @JsonProperty("StakingBlockNum")
    private BigInteger stakingBlockNum;

    /**
     * 当前候选人总共质押加被委托的von数目
     */
    @JsonProperty("Shares")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger shares;

    /**
     * 发起质押账户的自由金额的锁定期质押的von
     */
    @JsonProperty("Released")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger released;

    /**
     * 发起质押账户的自由金额的犹豫期质押的von
     */
    @JsonProperty("ReleasedHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger releasedHes;

    /**
     * 发起质押账户的锁仓金额的锁定期质押的von
     */
    @JsonProperty("RestrictingPlan")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger restrictingPlan;

    /**
     * 发起质押账户的锁仓金额的犹豫期质押的von
     */
    @JsonProperty("RestrictingPlanHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger restrictingPlanHes;

    /**
     * 节点最后一次被委托的结算周期数
     */
    @JsonProperty("DelegateEpoch")
    private BigInteger delegateEpoch;

    /**
     * 节点被委托的生效总数量
     */
    @JsonProperty("DelegateTotal")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateTotal;

    /**
     * 节点被委托的未生效的总数量
     */
    @JsonProperty("DelegateTotalHes")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateTotalHes;

    /**
     * 候选人当前发放的总委托奖励
     */
    @JsonProperty("DelegateRewardTotal")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateRewardTotal;

    /**
     * 外部Id(有长度限制，给第三方拉取节点描述的Id)
     */
    @JsonProperty("ExternalId")
    private String externalId;

    /**
     * 被质押节点的名称(有长度限制，表示该节点的名称)
     */
    @JsonProperty("NodeName")
    private String nodeName;

    /**
     * 节点的第三方主页(有长度限制，表示该节点的主页)
     */
    @JsonProperty("Website")
    private String website;

    /**
     * 节点的描述(有长度限制，表示该节点的描述)
     */
    @JsonProperty("Details")
    private String details;
}
