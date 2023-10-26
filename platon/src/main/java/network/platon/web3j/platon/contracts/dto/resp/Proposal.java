package network.platon.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Proposal {

    /**
     * 提案id
     */
    @JsonProperty("ProposalID")
    private String proposalId;
    /**
     * 提案节点ID
     */
    @JsonProperty("Proposer")
    private String proposer;
    /**
     * 提案类型， 0x01：文本提案； 0x02：升级提案；0x03参数提案
     */
    @JsonProperty("ProposalType")
    private int proposalType;
    /**
     * 提案PIPID
     */
    @JsonProperty("PIPID")
    private String piPid;
    /**
     * 提交提案的块高
     */
    @JsonProperty("SubmitBlock")
    private BigInteger submitBlock;
    /**
     * 提案投票结束的块高
     */
    @JsonProperty("EndVotingBlock")
    private BigInteger endVotingBlock;
    /**
     * 投票持续的共识周期数量
     */
    @JsonProperty("EndVotingRounds")
    private BigInteger endVotingRounds;
    /**
     * （如果投票通过）生效块高（endVotingBlock + 20 + 4*250 < 生效块高 <= endVotingBlock + 20 + 10*250）
     */
    @JsonProperty("ActiveBlock")
    private BigInteger activeBlock;
    /**
     * 升级版本
     */
    @JsonProperty("NewVersion")
    private BigInteger newVersion;
    /**
     * 提案要取消的升级提案ID
     */
    @JsonProperty("TobeCanceled")
    private String toBeCanceled;

    /**
     * 参数模块
     */
    @JsonProperty("Module")
    private String module;
    /**
     * 参数名称
     */
    @JsonProperty("Name")
    private String name;
    /**
     * 参数新值
     */
    @JsonProperty("NewValue")
    private String newValue;
}
