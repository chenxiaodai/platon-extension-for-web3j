package io.github.chenxiaodai.web3j.platon.contracts;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.chenxiaodai.web3j.platon.PlatONConstant;
import io.github.chenxiaodai.web3j.platon.contracts.common.ErrorCode;
import io.github.chenxiaodai.web3j.platon.contracts.common.Function;
import io.github.chenxiaodai.web3j.platon.contracts.common.GasProvider;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.utils.PPOSFuncUtils;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.JsonRpcError;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Slf4j
public abstract class BaseContract extends ManagedTransaction {

    protected String contractAddress;
    protected GasProvider gasProvider;
    protected ObjectMapper mapper = new ObjectMapper();

    protected BaseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
        this.contractAddress = contractAddress;
        this.gasProvider = new GasProvider(web3j);
    }

    protected BaseContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        this(contractAddress, web3j, new RawTransactionManager(web3j, credentials, chainId, PlatONConstant.ATTEMPTS, PlatONConstant.SLEEP_DURATION));
    }

    protected BaseContract(String contractAddress, Web3j web3j) {
        this(contractAddress, web3j, new ReadonlyTransactionManager(web3j, contractAddress));
    }

    protected RemoteCall<TransactionResponse> executeRemoteCallTransaction(Function function) {
        return new RemoteCall<>(() -> executeTransaction(function));
    }

    private TransactionResponse executeTransaction(Function function) throws TransactionException, IOException {

        TransactionReceipt receipt = null;
        String data = PPOSFuncUtils.encode(function);
        try {

            Transaction transaction = Transaction.createEthCallTransaction(transactionManager.getFromAddress(), contractAddress, data);
            receipt = send(contractAddress, data, BigInteger.ZERO, gasProvider.getGasPrice(function.getType()),
                            gasProvider.getGasLimit(transaction),
                            false);
        } catch (JsonRpcError error) {

            if (error.getData() != null) {
                throw new TransactionException(error.getData().toString());
            } else {
                throw new TransactionException(
                        String.format(
                                "JsonRpcError thrown with code %d. Message: %s",
                                error.getCode(), error.getMessage()));
            }
        }
        return getResponseFromTransactionReceipt(receipt);
    }

    private TransactionResponse getResponseFromTransactionReceipt(TransactionReceipt transactionReceipt) throws TransactionException {
        List<Log> logs = transactionReceipt.getLogs();
        if(logs==null||logs.isEmpty()){
            throw new TransactionException("TransactionReceipt logs is empty");
        }

        String logData = logs.get(0).getData();
        if(null == logData || "".equals(logData) ){
            throw new TransactionException("TransactionReceipt log data is empty");
        }

        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(logData));
        List<RlpType> rlpList = ((RlpList)(rlp.getValues().get(0))).getValues();
        String decodedStatus = new String(((RlpString)rlpList.get(0)).getBytes());
        int statusCode = Integer.parseInt(decodedStatus);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCode(statusCode);
        transactionResponse.setTransactionReceipt(transactionReceipt);

        return transactionResponse;
    }

    protected List<RlpType> decodePPOSLog(TransactionReceipt transactionReceipt) throws TransactionException {
        List<Log> logs = transactionReceipt.getLogs();
        if(logs==null||logs.isEmpty()){
            throw new TransactionException("TransactionReceipt logs is empty");
        }

        String logData = logs.get(0).getData();
        if(null == logData || "".equals(logData) ){
            throw new TransactionException("TransactionReceipt logs[0].data is empty");
        }

        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(logData));
        List<RlpType> rlpList = ((RlpList)(rlp.getValues().get(0))).getValues();
        String decodedStatus = new String(((RlpString)rlpList.get(0)).getBytes());
        int statusCode = Integer.parseInt(decodedStatus);

        if(statusCode != ErrorCode.SUCCESS){
            throw new TransactionException("TransactionResponse code is 0");
        }
        return rlpList;
    }


    protected <T> RemoteCall<CallResponse<List<T>>> executeRemoteCallListValueReturn(Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallListValueReturn(function, returnType));
    }

    protected <T> RemoteCall<CallResponse<T>> executeRemoteCallSingleValueReturn(Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function, returnType));
    }

    private <T> CallResponse<List<T>> executeCallListValueReturn(Function function, Class<T> returnType) throws IOException {
        String resultStr = ethCall(function);
        JsonNode root = mapper.readTree(resultStr);
        int code = root.path("Code").asInt();
        if(ErrorCode.SUCCESS != code){
            CallResponse<List<T>> response = new CallResponse<>();
            response.setCode(code);
            return response;
        }
        JavaType itemJavaType = mapper.getTypeFactory().constructParametricType(List.class, returnType);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(CallResponse.class, itemJavaType);
        return mapper.readValue(resultStr, javaType);
    }

    @SuppressWarnings("unchecked")
    private <T> CallResponse<T> executeCallSingleValueReturn(Function function, Class<T> returnType) throws IOException {
        String resultStr = ethCall(function);
        JsonNode root = mapper.readTree(resultStr);
        int code = root.path("Code").asInt();
        if(ErrorCode.SUCCESS != code){
            CallResponse<T> response = new CallResponse<>();
            response.setCode(code);
            return response;
        }
        if(BigInteger.class.isAssignableFrom(returnType)){
            CallResponse<T> response = new CallResponse<>();
            response.setCode(code);
            JsonNode retNode = root.path("Ret");
            response.setData((T) Numeric.toBigInt(retNode.textValue()));
            return response;
        }
        JavaType javaType = mapper.getTypeFactory().constructParametricType(CallResponse.class, returnType);
        return mapper.readValue(resultStr, javaType);
    }

    private String ethCall(Function function) throws IOException {
        EthCall ethCall = web3j.ethCall(
                        Transaction.createEthCallTransaction(transactionManager.getFromAddress(), contractAddress, PPOSFuncUtils.encode(function)),
                        DefaultBlockParameterName.LATEST)
                .send();

        //判断底层返回的错误信息是否包含超时信息
        if (ethCall.hasError()) {
            Response.Error error = ethCall.getError();
            throw new ContractCallException( String.format(
                    "JsonRpcError thrown with code %d. Message: %s",
                    error.getCode(), error.getMessage()));
        }

        String result = Numeric.cleanHexPrefix(ethCall.getValue());
        if (Strings.isBlank(result)) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }
        System.out.println(new String(Numeric.hexStringToByteArray(result)));
        return new String(Numeric.hexStringToByteArray(result));
    }
}
