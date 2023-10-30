package network.platon.web3j.splaton.contracts;

import network.platon.web3j.platon.contracts.RestrictingContract;
import network.platon.web3j.platon.contracts.common.Function;
import network.platon.web3j.platon.contracts.dto.CallResponse;
import network.platon.web3j.platon.contracts.enums.InnerContractEnum;
import network.platon.web3j.platon.contracts.type.StringType;
import network.platon.web3j.platon.contracts.type.Type;
import network.platon.web3j.platon.contracts.utils.PPOSFuncUtils;
import network.platon.web3j.platon.utils.Bech32Utils;
import network.platon.web3j.splaton.contracts.dto.resp.Balance;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.TransactionManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SPlatONRestrictingContract extends RestrictingContract {

    public final static int FUNC_GET_RESTRICTING_INFO = 4200;

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static SPlatONRestrictingContract load(Web3j web3j) {
        return new SPlatONRestrictingContract(InnerContractEnum.RESTRICTING_CONTRACT.getAddress(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static SPlatONRestrictingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new SPlatONRestrictingContract(InnerContractEnum.RESTRICTING_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
     */
    public static SPlatONRestrictingContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new SPlatONRestrictingContract(InnerContractEnum.RESTRICTING_CONTRACT.getAddress(), web3j, credentials, chainId);
    }

    protected SPlatONRestrictingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    protected SPlatONRestrictingContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    protected SPlatONRestrictingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 批量查询账号余额
     * @param addresses 地址列表，使用逗号分隔
     * @return 账号详情列表
     */
    public RemoteCall<CallResponse<List<Balance>>> batchGetBalance(String addresses) {
        return executeRemoteCallListValueReturn(getFunctionOfBatchGetBalance(addresses), Balance.class);
    }

    public static String encodeTransactionDataOfBatchGetBalance(String addresses){
        return PPOSFuncUtils.encode(getFunctionOfBatchGetBalance(addresses));
    }

    private static Function getFunctionOfBatchGetBalance(String addresses){
        List<String> addressList = Arrays.asList(addresses.split(","));
        List<String> converted = addressList.stream().map(Bech32Utils::encodeWithLat).collect(Collectors.toList());
        List<Type> param = Arrays.asList(
                new StringType(String.join(",", converted))
        );
        return new Function(FUNC_GET_RESTRICTING_INFO, param);
    }
}
