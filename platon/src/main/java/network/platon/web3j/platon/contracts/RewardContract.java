package network.platon.web3j.platon.contracts;

import network.platon.web3j.platon.contracts.common.Function;
import network.platon.web3j.platon.contracts.dto.CallResponse;
import network.platon.web3j.platon.contracts.dto.TransactionResponse;
import network.platon.web3j.platon.contracts.dto.resp.Reward;
import network.platon.web3j.platon.contracts.enums.InnerContractEnum;
import network.platon.web3j.platon.contracts.type.HexStringType;
import network.platon.web3j.platon.contracts.type.ListType;
import network.platon.web3j.platon.contracts.type.Type;
import network.platon.web3j.platon.contracts.utils.PPOSFuncUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.TransactionManager;

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
        return executeRemoteCallTransaction(getFunctionOfWithdrawDelegateReward());
    }

    public static String encodeTransactionDataOfWithdrawDelegateReward(){
        return PPOSFuncUtils.encode(getFunctionOfWithdrawDelegateReward());
    }

    private static Function getFunctionOfWithdrawDelegateReward(){
        return new Function(FUNC_WITHDRAW_DELEGATE_REWARD);
    }

    /**
     *  获得提取的明细（当提取账户当前所有的可提取的委托奖励成功时调用）
     *
     * @param transactionReceipt 交易回执
     * @return 奖励信息
     * @throws TransactionException 交易异常
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
     * @param account 委托的账号
     * @param nodeList 委托的节点id列表
     * @return 提案列表
     */
    public RemoteCall<CallResponse<List<Reward>>> getDelegateReward(String account, List<String> nodeList) {
        return executeRemoteCallListValueReturn(getFunctionOfGetDelegateReward(account, nodeList), Reward.class);
    }

    public static String encodeTransactionDataOfGetDelegateReward(String account, List<String> nodeList){
        return PPOSFuncUtils.encode(getFunctionOfGetDelegateReward(account, nodeList));
    }

    private static Function getFunctionOfGetDelegateReward(String account, List<String> nodeList){
        List<Type> param = Arrays.asList(
                new HexStringType(account),
                new ListType<>(nodeList.stream().map(HexStringType::new).collect(Collectors.toList()))
        );
        return new Function(FUNC_GET_DELEGATE_REWARD, param);
    }
}
