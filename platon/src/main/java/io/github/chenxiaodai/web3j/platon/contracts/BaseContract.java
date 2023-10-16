package io.github.chenxiaodai.web3j.platon.contracts;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.chenxiaodai.web3j.platon.PlatONConstant;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.type.Type;
import io.github.chenxiaodai.web3j.platon.utils.PPOSFuncUtils;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.utils.Numeric;
import org.web3j.utils.Strings;

import java.io.IOException;
import java.util.List;

@Slf4j
public abstract class BaseContract extends ManagedTransaction {

    protected String contractAddress;
    protected ObjectMapper mapper = new ObjectMapper();

    protected BaseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
        this.contractAddress = contractAddress;
    }

    protected BaseContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        this(contractAddress, web3j, new RawTransactionManager(web3j, credentials, chainId, PlatONConstant.ATTEMPTS, PlatONConstant.SLEEP_DURATION));
    }

    protected BaseContract(String contractAddress, Web3j web3j) {
        this(contractAddress, web3j, new ReadonlyTransactionManager(web3j, contractAddress));
    }

    protected <T> RemoteCall<CallResponse<List<T>>> executeRemoteCallListValueReturn(List<Type> params, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallListValueReturn(params, returnType));
    }

    protected <T> RemoteCall<CallResponse<T>> executeRemoteCallSingleValueReturn(List<Type> params, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(params, returnType));
    }

    private <T> CallResponse<List<T>> executeCallListValueReturn(List<Type> params, Class<T> returnType) throws IOException {
        String resultStr = ethCall(params);
        JavaType itemJavaType = mapper.getTypeFactory().constructParametricType(List.class, returnType);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(CallResponse.class, itemJavaType);
        return mapper.readValue(resultStr, javaType);
    }


    private <T> CallResponse<T> executeCallSingleValueReturn(List<Type> params, Class<T> returnType) throws IOException {
        String resultStr = ethCall(params);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(CallResponse.class, returnType);
        return mapper.readValue(resultStr, javaType);
    }

    private String ethCall(List<Type> params) throws IOException {
        EthCall ethCall = web3j.ethCall(
                        Transaction.createEthCallTransaction(transactionManager.getFromAddress(), contractAddress, PPOSFuncUtils.encode(params)),
                        DefaultBlockParameterName.LATEST)
                .send();

        //判断底层返回的错误信息是否包含超时信息
        if (ethCall.hasError()) {
            Response.Error error = ethCall.getError();
            throw new ContractCallException("Rpc error code = " + error.getCode() + " message = " + error.getMessage());
        }

        String result = Numeric.cleanHexPrefix(ethCall.getValue());
        if (Strings.isBlank(result)) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }
        return new String(Numeric.hexStringToByteArray(result));
    }
}
