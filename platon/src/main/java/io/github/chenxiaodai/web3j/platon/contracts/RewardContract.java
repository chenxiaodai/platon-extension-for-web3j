package io.github.chenxiaodai.web3j.platon.contracts;

import io.github.chenxiaodai.web3j.platon.contracts.common.ErrorCode;
import io.github.chenxiaodai.web3j.platon.contracts.common.Function;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.req.RestrictingPlan;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.RestrictingItem;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.Reward;
import io.github.chenxiaodai.web3j.platon.contracts.enums.InnerContractEnum;
import io.github.chenxiaodai.web3j.platon.contracts.type.HexStringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.ListType;
import io.github.chenxiaodai.web3j.platon.contracts.type.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RewardContract extends BaseContract {

    public final static int FUNC_WITHDRAW_DELEGATE_REWARD = 5000;
    public final static int FUNC_GET_DELEGATE_REWARD = 5100;

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static RewardContract load(Web3j web3j) {
        return new RewardContract(InnerContractEnum.DELEGATE_REWARD_CONTRACT.getAddress(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static RewardContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new RewardContract(InnerContractEnum.DELEGATE_REWARD_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
     */
    public static RewardContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new RewardContract(InnerContractEnum.DELEGATE_REWARD_CONTRACT.getAddress(), web3j, credentials, chainId);
    }

    private RewardContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private RewardContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    private RewardContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 提取账户当前所有的可提取的委托奖励
     *
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> withdrawDelegateReward() {
        Function function = new Function(FUNC_WITHDRAW_DELEGATE_REWARD);
        return executeRemoteCallTransaction(function);
    }

    /**
     *  获得提取的明细（当提取账户当前所有的可提取的委托奖励成功时调用）
     *
     * @param transactionReceipt
     * @return
     * @throws TransactionException
     */
    public List<Reward> decodeWithdrawDelegateRewardLog(TransactionReceipt transactionReceipt) throws TransactionException {
        List<RlpType> rlpList  = decodePPOSLog(transactionReceipt);
        List<Reward> rewards = new ArrayList<>();
        ((RlpList) RlpDecoder.decode(((RlpString)rlpList.get(1)).getBytes()).getValues().get(0)).getValues()
                .stream()
                .forEach(rl -> {
                    RlpList rlpL = (RlpList)rl;
                    Reward reward = new Reward();
                    reward.setNodeId(((RlpString)rlpL.getValues().get(0)).asString());
                    reward.setStakingNum(((RlpString)rlpL.getValues().get(1)).asPositiveBigInteger());
                    reward.setRewardBigIntegerValue((((RlpString)rlpL.getValues().get(2)).asPositiveBigInteger()));
                    rewards.add(reward);
                });

        return  rewards;
    }

    /**
     * 查询账户在各节点未提取委托奖励
     *
     * @param account 锁仓释放到账账户
     * @return 提案列表
     */
    public RemoteCall<CallResponse<List<Reward>>> getDelegateReward(String account, List<String> nodeList) {
        List<Type> param = Arrays.asList(
                new HexStringType(account),
                new ListType<>(nodeList.stream().map(HexStringType::new).collect(Collectors.toList()))
        );
        Function function = new Function(FUNC_GET_DELEGATE_REWARD, param);
        return executeRemoteCallListValueReturn(function, Reward.class);
    }
}
