package io.github.chenxiaodai.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.chenxiaodai.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import io.github.chenxiaodai.web3j.platon.contracts.converter.HexString2BigIntegerConverter;
import lombok.Data;

import java.math.BigInteger;


@Data
public class VerifierNode {

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

    /**
     * 验证人的任期(在结算周期的101个验证人快照中永远是0，只有在共识轮的验证人时才会被有值，刚被选出来时也是0，继续留任时则+1)
     */
    @JsonProperty("ValidatorTerm")
    private String validatorTerm;

    /**
     * 节点被委托的生效总数量
     */
    @JsonProperty("DelegateTotal")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateTotal;

    /**
     * 候选人当前发放的总委托奖励
     */
    @JsonProperty("DelegateRewardTotal")
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger delegateRewardTotal;
}
