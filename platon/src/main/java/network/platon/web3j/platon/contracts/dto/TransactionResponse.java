package network.platon.web3j.platon.contracts.dto;

import lombok.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionResponse extends BaseResponse {

    private TransactionReceipt transactionReceipt;
}
