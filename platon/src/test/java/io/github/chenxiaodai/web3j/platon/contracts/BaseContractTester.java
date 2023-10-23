package io.github.chenxiaodai.web3j.platon.contracts;

import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.req.RestrictingPlan;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.RestrictingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.TxHashVerifier;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class BaseContractTester {

    protected Web3j web3j;
    protected TxHashVerifier txHashVerifier;

    @BeforeEach
    public void setUp() {
        web3j = mock(Web3j.class);
        txHashVerifier = mock(TxHashVerifier.class);
        when(txHashVerifier.verify(any(), any())).thenReturn(true);
    }

    protected TransactionManager getVerifiedTransactionManager(Credentials credentials) {
        RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 2022041902);
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    @SuppressWarnings("unchecked")
    protected void prepareCall(String result) throws IOException {
        EthCall ethCall = new EthCall();
        ethCall.setResult(result);
        Request<?, EthCall> request = mock(Request.class);
        when(request.send())
                .thenReturn(ethCall);
        when(web3j.ethCall(any(), any()))
                .thenReturn((Request) request);
    }

    protected void prepareTransaction() throws IOException {
        prepareGasLimitRequest();
        prepareGasPriceRequest();
        prepareNonceRequest();
        prepareTransactionRequest();
        prepareTransactionReceipt();
    }

    @SuppressWarnings("unchecked")
    private void prepareGasLimitRequest() throws IOException {
        EthEstimateGas gasLimit = new EthEstimateGas();
        gasLimit.setResult("0x1");

        Request<?, EthEstimateGas> gasLimitRequest = mock(Request.class);
        when(gasLimitRequest.send())
                .thenReturn(gasLimit);
        when(web3j.ethEstimateGas(any()))
                .thenReturn((Request) gasLimitRequest);
    }

    @SuppressWarnings("unchecked")
    private void prepareGasPriceRequest() throws IOException {
        EthGasPrice ethGasPrice = new EthGasPrice();
        ethGasPrice.setResult("0x1");

        Request<?, EthGasPrice> gasPriceRequest = mock(Request.class);
        when(gasPriceRequest.send())
                .thenReturn(ethGasPrice);
        when(web3j.ethGasPrice())
                .thenReturn((Request) gasPriceRequest);
    }


    @SuppressWarnings("unchecked")
    private void prepareNonceRequest() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = new EthGetTransactionCount();
        ethGetTransactionCount.setResult("0x1");

        Request<?, EthGetTransactionCount> transactionCountRequest = mock(Request.class);
        when(transactionCountRequest.send())
                .thenReturn(ethGetTransactionCount);
        when(web3j.ethGetTransactionCount(any(), any()))
                .thenReturn((Request) transactionCountRequest);
    }

    @SuppressWarnings("unchecked")
    private void prepareTransactionRequest() throws IOException {
        EthSendTransaction ethSendTransaction = new EthSendTransaction();
        ethSendTransaction.setResult("0x8a6c66c863b614ed19cace475f33f71c1038291881948fe668a23eff4ed56fe4");

        Request<?, EthSendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.send())
                .thenReturn(ethSendTransaction);
        when(web3j.ethSendRawTransaction(any()))
                .thenReturn((Request) rawTransactionRequest);
    }

    @SuppressWarnings("unchecked")
    private void prepareTransactionReceipt() throws IOException {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("0x8a6c66c863b614ed19cace475f33f71c1038291881948fe668a23eff4ed56fe4");
        transactionReceipt.setStatus("0x1");
        Log log = new Log();
        log.setData("0xc130");
        transactionReceipt.setLogs(Arrays.asList(log));

        EthGetTransactionReceipt ethGetTransactionReceipt = new EthGetTransactionReceipt();
        ethGetTransactionReceipt.setResult(transactionReceipt);

        Request<?, EthGetTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.send())
                .thenReturn(ethGetTransactionReceipt);
        when(web3j.ethGetTransactionReceipt(any()))
                .thenReturn((Request) getTransactionReceiptRequest);
    }
}
