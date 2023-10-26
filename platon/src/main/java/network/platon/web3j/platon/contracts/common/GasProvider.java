package network.platon.web3j.platon.contracts.common;

import network.platon.web3j.platon.contracts.GovContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.tx.exceptions.ContractCallException;

import java.io.IOException;
import java.math.BigInteger;

public class GasProvider {

    private Web3j web3j;

    public GasProvider(Web3j web3j) {
        this.web3j = web3j;
    }

    public BigInteger getGasPrice(Integer funcType) throws IOException {
        switch (funcType) {
            case GovContract.FUNC_SUBMIT_TEXT:
                return BigInteger.valueOf(1500000).multiply(BigInteger.valueOf(1000000000));
            case GovContract.FUNC_SUBMIT_VERSION:
                return BigInteger.valueOf(2100000).multiply(BigInteger.valueOf(1000000000));
            case GovContract.FUNC_SUBMIT_PARAM:
                return BigInteger.valueOf(2000000).multiply(BigInteger.valueOf(1000000000));
            case GovContract.FUNC_SUBMIT_CANCEL:
                return BigInteger.valueOf(3000000).multiply(BigInteger.valueOf(1000000000));
            default:
                return web3j.ethGasPrice().send().getGasPrice();
        }
    }

    public BigInteger getGasLimit(Transaction transaction) throws IOException {
        EthEstimateGas estimateGas = web3j.ethEstimateGas(transaction).send();
        if(estimateGas.hasError()){
            Response.Error error = estimateGas.getError();
            throw new ContractCallException( String.format(
                    "JsonRpcError thrown with code %d. Message: %s",
                    error.getCode(), error.getMessage()));
        }
        return estimateGas.getAmountUsed();
    }
}
