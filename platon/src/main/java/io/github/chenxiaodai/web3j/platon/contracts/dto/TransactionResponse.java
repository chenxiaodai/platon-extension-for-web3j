package io.github.chenxiaodai.web3j.platon.contracts.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Getter
@Setter
@ToString(callSuper = true)
public class TransactionResponse extends BaseResponse {

    private TransactionReceipt transactionReceipt;
}
