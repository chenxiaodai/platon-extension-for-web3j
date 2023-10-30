package network.platon.web3j.splaton.contracts.dto.resp;

import lombok.Data;

import java.util.List;

@Data
public class PPosInvokeContractInput {
    /**
     * PPOS调用输入数据
     */
    private List<TransData> transDatas;
    /**
     * 合约调用hash
     */
    private String txHash;
    /**
     * 调用方地址
     */
    private String from;
    /**
     * 内置PPOS合约地址
     */
    private String to;

    @Data
    public class TransData{
        private String input;
        private String code;
    }
}