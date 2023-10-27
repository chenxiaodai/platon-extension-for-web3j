package network.platon.web3j.platon.protocol.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import network.platon.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.core.Response;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DebugEconomicConfig extends Response<String> {

    public EconomicConfig getEconomicConfig() throws JsonProcessingException {
        return ObjectMapperFactory.getObjectMapper().readValue(getResult(), EconomicConfig.class);
    }

    @Data
    public static class EconomicConfig {
        /**
         * common的配置项
         */
        private Common common;
        /**
         * staking的配置项
         */
        private Staking staking;
        /**
         * slashing的配置项
         */
        private Slashing slashing;
        /**
         * gov的配置项
         */
        private Gov gov;
        /**
         * reward的配置项
         */
        private Reward reward;
        /**
         * innerAcc的配置项
         */
        private InnerAcc innerAcc;

        /**
         * config item for restricting plan
         */
        private Restricting restricting;

    }

    @Data
    public static class Common {
        /**
         * 结算周期规定的分钟数（整数）(eh)
         */
        private BigInteger maxEpochMinutes;
        /**
         * 共识轮验证人数
         */
        private BigInteger maxConsensusVals;
        /**
         * 增发周期的时间（分钟）
         */
        private BigInteger additionalCycleTime;
        /**
         * 底层内部调试用
         */
        private BigInteger nodeBlockTimeWindow;
        /**
         * 底层内部调试用
         */
        private BigInteger perRoundBlocks;
    }

    @Data
    public static class Staking {
        /**
         * 质押的von门槛（单位：Von）===>100w lat
         */
        private BigInteger stakeThreshold;
        /**
         * (incr, decr)委托或incr设置允许的最小阈值（单位：Von）===> 10 lat
         */
        private BigInteger operatingThreshold;
        /**
         * 结算周期验证人个数
         */
        private BigInteger maxValidators;
        /**
         * 退出质押后von被冻结的周期(单位： 结算周期，退出表示主动撤销和被动失去资格)
         */
        private BigInteger unStakeFreezeDuration;
        private BigInteger rewardPerMaxChangeRange;
        private BigInteger rewardPerChangeInterval;
        /**
         * 委托冻结周期数
         */
        private BigInteger unDelegateFreezeDuration;
    }

    @Data
    public static class Slashing {

        /**
         * 双签高处罚金额，万分比（‱）
         */
        private BigInteger slashFractionDuplicateSign;
        /**
         * 表示从扣除的惩罚金里面，拿出x%奖励给举报者（%）
         */
        private BigDecimal duplicateSignReportReward;
        /**
         * 零出块率惩罚的区块奖励数
         */
        private BigInteger slashBlocksReward;
        /**
         * 证据有效期
         */
        private BigInteger maxEvidenceAge;

        private BigInteger zeroProduceCumulativeTime;
        private BigInteger zeroProduceNumberThreshold;
        // 节点零出块惩罚被锁定时间
        private BigInteger zeroProduceFreezeDuration;
    }

    @Data
    public static class Gov {
        /**
         * 升级提案的投票持续最长的时间（单位：s）
         */
        private BigInteger versionProposalVoteDurationSeconds;
        /**
         * 升级提案投票支持率阈值（大于等于此值，则升级提案投票通过）
         */
        private BigDecimal versionProposalSupportRate;
        /**
         * 文本提案的投票持续最长的时间（单位：s）
         */
        private BigInteger textProposalVoteDurationSeconds;
        /**
         * 文本提案投票参与率阈值（文本提案投票通过条件之一：大于此值，则文本提案投票通过）
         */
        private BigDecimal textProposalVoteRate;
        /**
         * 文本提案投票支持率阈值（文本提案投票通过条件之一：大于等于此值，则文本提案投票通过）
         */
        private BigDecimal textProposalSupportRate;
        /**
         * 取消提案投票参与率阈值（取消提案投票通过条件之一：大于此值，则取消提案投票通过）
         */
        private BigDecimal cancelProposalVoteRate;
        /**
         * 取消提案投票支持率阈值（取消提案投票通过条件之一：大于等于此值，则取消提案投票通过）
         */
        private BigDecimal cancelProposalSupportRate;
        /**
         * 参数提案的投票持续最长的时间（单位：s）
         */
        private BigInteger paramProposalVoteDurationSeconds;
        /**
         * 参数提案投票参与率阈值（参数提案投票通过条件之一：大于此值，则参数提案投票通过)
         */
        private BigDecimal paramProposalVoteRate;
        /**
         * 参数提案投票支持率阈值（参数提案投票通过条件之一：大于等于此值，则参数提案投票通过
         */
        private BigDecimal paramProposalSupportRate;
    }

    @Data
    public static class Reward {
        /**
         * 奖励池分配给出块奖励的比例，剩下的比例为分配给质押的奖励（%）
         */
        private BigInteger newBlockRate;
        /**
         * 基金会分配年，代表基金会每年边界的百分比
         */
        private BigInteger platonFoundationYear;

        private BigInteger increaseIssuanceRatio;

        private BigInteger theNumberOfDelegationsReward;

    }

    @Data
    public static class InnerAcc {

        /**
         * 基金会账号地址
         */
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String platonFundAccount;
        /**
         * 基金会初始金额
         */
        private BigInteger platonFundBalance;
        /**
         * 社区开发者账户
         */
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String cdfAccount;
        /**
         * 社区开发者初始金额
         */
        private BigInteger cdfBalance;
    }

    @Data
    public static class Restricting {
        /**
         * minimum of each releasing of restricting plan
         */
        private BigInteger minimumRelease;
    }

}