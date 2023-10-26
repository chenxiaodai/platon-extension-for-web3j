package network.platon.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigInteger;

/**
 * 投票结果
 */
@Data
public class TallyResult {
    /**
     * 提案ID
     */
    @JsonProperty("proposalID")
    private String proposalId;
    /**
     * 赞成票
     */
    private BigInteger yeas;
    /**
     * 反对票
     */
    private BigInteger nays;
    /**
     * 弃权票
     */
    private BigInteger abstentions;
    /**
     * 在整个投票期内有投票资格的验证人总数
     */
    private BigInteger accuVerifiers;
    /**
     * 状态
     */
    private int status;
    /**
     * 当status=0x06时，记录发起取消的ProposalID
     */
    private String canceledBy;
}
