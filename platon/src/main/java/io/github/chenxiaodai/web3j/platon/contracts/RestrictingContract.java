package io.github.chenxiaodai.web3j.platon.contracts;

import io.github.chenxiaodai.web3j.platon.contracts.common.Function;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.req.RestrictingPlan;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.RestrictingItem;
import io.github.chenxiaodai.web3j.platon.contracts.enums.InnerContractEnum;
import io.github.chenxiaodai.web3j.platon.contracts.type.HexStringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.ListType;
import io.github.chenxiaodai.web3j.platon.contracts.type.Type;
import io.github.chenxiaodai.web3j.platon.contracts.utils.PPOSFuncUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.TransactionManager;

import java.util.Arrays;
import java.util.List;

public class RestrictingContract extends BaseContract {

    public final static int FUNC_CREATE_RESTRICTING_PLAN = 4000;
    public final static int FUNC_GET_RESTRICTING_INFO = 4100;

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static RestrictingContract load(Web3j web3j) {
        return new RestrictingContract(InnerContractEnum.RESTRICTING_CONTRACT.getAddress(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static RestrictingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new RestrictingContract(InnerContractEnum.RESTRICTING_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
     */
    public static RestrictingContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new RestrictingContract(InnerContractEnum.RESTRICTING_CONTRACT.getAddress(), web3j, credentials, chainId);
    }

    private RestrictingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private RestrictingContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    private RestrictingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 创建锁仓计划
     *
     * @param account 锁仓释放到账账户
     * @param planList 锁仓计划
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> createRestrictingPlan(String account, List<RestrictingPlan> planList) {
        return executeRemoteCallTransaction(getFunctionOfCreateRestrictingPlan(account, planList));
    }

    public static String encodeTransactionDataOfCreateRestrictingPlan(String account, List<RestrictingPlan> planList){
        return PPOSFuncUtils.encode(getFunctionOfCreateRestrictingPlan(account, planList));
    }

    private static Function getFunctionOfCreateRestrictingPlan(String account, List<RestrictingPlan> planList){
        List<Type> param = Arrays.asList(
                new HexStringType(account),
                new ListType<>(planList)
        );
        return new Function(FUNC_CREATE_RESTRICTING_PLAN, param);
    }

    /**
     * 获取锁仓信息
     *
     * @param account 锁仓释放到账账户
     * @return 提案列表
     */
    public RemoteCall<CallResponse<RestrictingItem>> getRestrictingInfo(String account) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetRestrictingInfo(account), RestrictingItem.class);
    }

    public static String encodeTransactionDataOfGetRestrictingInfo(String account){
        return PPOSFuncUtils.encode(getFunctionOfGetRestrictingInfo(account));
    }

    private static Function getFunctionOfGetRestrictingInfo(String account){
        List<Type> param = Arrays.asList(
                new HexStringType(account)
        );
        return new Function(FUNC_GET_RESTRICTING_INFO, param);
    }
}
