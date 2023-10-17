package io.github.chenxiaodai.web3j.platon.contracts;

import io.github.chenxiaodai.web3j.platon.contracts.common.Function;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.Node;
import io.github.chenxiaodai.web3j.platon.contracts.enums.CreateStakingAmountTypeEnum;
import io.github.chenxiaodai.web3j.platon.contracts.enums.DelegateAmountTypeEnum;
import io.github.chenxiaodai.web3j.platon.contracts.type.HexStringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.StringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.Type;
import io.github.chenxiaodai.web3j.platon.contracts.type.UintType;
import io.github.chenxiaodai.web3j.platon.contracts.enums.IncreaseStakingAmountTypeEnum;
import io.github.chenxiaodai.web3j.platon.enums.InnerContractEnum;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class StakingContract extends BaseContract {

    public final static int FUNC_CREATE_STAKING = 1000;
    public final static int FUNC_EDIT_CANDIDATE = 1001;
    public final static int FUNC_INCREASE_STAKING = 1002;
    public final static int FUNC_WITHDREW_STAKING = 1003;
    public final static int FUNC_DELEGATE = 1004;
    public final static int FUNC_GET_CANDIDATE_LIST = 1102;
    public final static int FUNC_GET_CANDIDATE_INFO = 1105;

	
	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j
	 * @return
	 */
    public static StakingContract load(Web3j web3j) {
        return new StakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static StakingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new StakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j
     * @param credentials
     * @return
     */
    public static StakingContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new StakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j, credentials, chainId);
    }
    
    private StakingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private StakingContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    private StakingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 发起质押
     *
     * @param nodeId 被质押的节点Id(也叫候选人的节点Id)
     * @param type 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额；2: 默认优先使用锁仓金额，当质押金额大于锁仓金额时才使用自由金额
     * @param benefitAddress 用于接受出块奖励和质押奖励的收益账户
     * @param rewardPer 委托所得到的奖励分成比例，采用BasePoint 1BP=0.01%，例：传500就是5%的奖励作为委托奖励
     * @param externalId 外部Id(有长度限制，给第三方拉取节点描述的Id)
     * @param nodeName 被质押节点的名称(有长度限制，表示该节点的名称)
     * @param website 节点的第三方主页(有长度限制，表示该节点的主页)
     * @param details 节点的描述(有长度限制，表示该节点的描述)
     * @param programVersion  程序的真实版本，治理rpc获取
     * @param programVersionSign 程序的真实版本签名，治理rpc获取
     * @param blsPubKey bls的公钥
     * @param blsProof bls的证明,通过拉取证明接口获取
     * @return
     */
    public RemoteCall<TransactionResponse> createStaking(String nodeId, CreateStakingAmountTypeEnum type, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details, BigInteger amount, BigInteger programVersion, String programVersionSign, String blsPubKey, String blsProof) {
        List<Type> param = Arrays.asList(
                new UintType(type.getValue()),
                    new HexStringType(benefitAddress),
                new HexStringType(nodeId),
                new StringType(externalId),
                new StringType(nodeName),
                new StringType(website),
                new StringType(details),
                new UintType(amount),
                new UintType(rewardPer),
                new UintType(programVersion),
                new HexStringType(programVersionSign),
                new HexStringType(blsPubKey),
                new HexStringType(blsProof)
        );
        Function function = new Function(FUNC_CREATE_STAKING, param);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 修改质押信息
     *
     * @param nodeId 被质押的节点Id(也叫候选人的节点Id)
     * @param benefitAddress 用于接受出块奖励和质押奖励的收益账户
     * @param rewardPer 委托所得到的奖励分成比例，采用BasePoint 1BP=0.01%，例：传500就是5%的奖励作为委托奖励
     * @param externalId 外部Id(有长度限制，给第三方拉取节点描述的Id)
     * @param nodeName 被质押节点的名称(有长度限制，表示该节点的名称)
     * @param website 节点的第三方主页(有长度限制，表示该节点的主页)
     * @param details 节点的描述(有长度限制，表示该节点的描述)
     * @return
     */
    public RemoteCall<TransactionResponse> editCandidate(String nodeId, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details) {
        List<Type> param = Arrays.asList(
                new HexStringType(benefitAddress),
                new HexStringType(nodeId),
                new UintType(rewardPer),
                new StringType(externalId),
                new StringType(nodeName),
                new StringType(website),
                new StringType(details)
        );
        Function function = new Function(FUNC_EDIT_CANDIDATE, param);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 增持质押
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @param type              表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * @param amount            增持的von
     * @return
     */
    public RemoteCall<TransactionResponse> increaseStaking(String nodeId, IncreaseStakingAmountTypeEnum type, BigInteger amount) {
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new UintType(type.getValue()),
                new UintType(amount)
        );
        Function function = new Function(FUNC_INCREASE_STAKING, param);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 撤销质押(一次性发起全部撤销，多次到账)
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<TransactionResponse> withdrewStaking(String nodeId) {
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId)
        );
        Function function = new Function(FUNC_WITHDREW_STAKING, param);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            委托的节点Id
     * @param type              表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<TransactionResponse> delegate(String nodeId, DelegateAmountTypeEnum type, BigInteger amount) {
        List<Type> param = Arrays.asList(
                new UintType(type.getValue()),
                new HexStringType(nodeId),
                new UintType(amount)
        );
        Function function = new Function(FUNC_DELEGATE, param);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId            委托的节点Id
     * @param type              表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<TransactionResponse> withdrewDelegation(String nodeId, DelegateAmountTypeEnum type, BigInteger amount) {
        List<Type> param = Arrays.asList(
                new UintType(type.getValue()),
                new HexStringType(nodeId),
                new UintType(amount)
        );
        Function function = new Function(FUNC_DELEGATE, param);
        return executeRemoteCallTransaction(function);
    }


    /**
     * 查询所有实时的候选人列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getCandidateList() {
        Function function = new Function(FUNC_GET_CANDIDATE_LIST);
        return executeRemoteCallListValueReturn(function, Node.class);
    }

    /**
     * 获取质押信息
     *
     * @param nodeId
     * @return
     */
    public RemoteCall<CallResponse<Node>> getStakingInfo(String nodeId) {
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId)
        );
        Function function = new Function(FUNC_GET_CANDIDATE_INFO, param);
        return executeRemoteCallSingleValueReturn(function, Node.class);
    }




//
//    /**
//     * 查询当前结算周期的区块奖励
//     *
//     * @return
//     */
//    public RemoteCall<CallResponse<BigInteger>> getPackageReward() {
//    	Function function = new Function(FunctionType.GET_PACKAGEREWARD_FUNC_TYPE);
//        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
//    }
//
//    /**
//     * 查询当前结算周期的质押奖励
//     *
//     * @return
//     */
//    public RemoteCall<CallResponse<BigInteger>> getStakingReward() {
//    	Function function = new Function(FunctionType.GET_STAKINGREWARD_FUNC_TYPE);
//        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
//    }
//
//    /**
//     * 查询打包区块的平均时间
//     *
//     * @return
//     */
//    public RemoteCall<CallResponse<BigInteger>> getAvgPackTime() {
//        Function function = new Function(FunctionType.GET_AVGPACKTIME_FUNC_TYPE);
//        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
//    }
//
//    /**
//     * 发起质押
//     *
//     * @param stakingParam
//     * @return
//     * @see StakingParam
//     */
//    public RemoteCall<TransactionResponse> staking(StakingParam stakingParam) {
//        Function function = createStakingFunction(stakingParam);
//        return executeRemoteCallTransaction(function);
//    }
//
//    /**
//     * 发起质押
//     *
//     * @param stakingParam
//     * @param gasProvider
//     * @return
//     * @see StakingParam
//     */
//    public RemoteCall<TransactionResponse> staking(StakingParam stakingParam, GasProvider gasProvider)  {
//        Function function = createStakingFunction(stakingParam);
//        return executeRemoteCallTransaction(function, gasProvider);
//    }
//
//    /**
//     * 获取质押gasProvider
//     *
//     * @param stakingParam
//     * @return
//     */
//    public GasProvider getStakingGasProvider(StakingParam stakingParam) throws IOException, EstimateGasException, NoSupportFunctionType {
//        Function function = createStakingFunction(stakingParam);
//        return getDefaultGasProvider(function);
//    }
//
//
//    /**
//     * 发起质押
//     *
//     * @param stakingParam
//     * @return
//     * @see StakingParam
//     */
//    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam)  {
//        Function function = createStakingFunction(stakingParam);
//        return executeRemoteCallTransactionStep1(function);
//    }
//
//    /**
//     * 发起质押
//     *
//     * @param stakingParam
//     * @param gasProvider
//     * @return
//     * @see StakingParam
//     */
//    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam, GasProvider gasProvider) {
//        Function function = createStakingFunction(stakingParam);
//        return executeRemoteCallTransactionStep1(function, gasProvider);
//    }
//
//    private Function createStakingFunction(StakingParam stakingParam)  {
//        Function function = new Function(
//                FunctionType.STAKING_FUNC_TYPE,
//                stakingParam.getSubmitInputParameters());
//        return function;
//    }
//
//    /**
//     * 撤销质押
//     *
//     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
//     * @return
//     */
//    public RemoteCall<TransactionResponse> unStaking(String nodeId) {
//        Function function = createUnStakingFunction(nodeId);
//        return executeRemoteCallTransaction(function);
//    }
//
//    /**
//     * 撤销质押
//     *
//     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
//     * @return
//     */
//    public RemoteCall<TransactionResponse> unStaking(String nodeId, GasProvider gasProvider) {
//        Function function = createUnStakingFunction(nodeId);
//        return executeRemoteCallTransaction(function, gasProvider);
//    }
//
//    /**
//     * 获取撤销质押的gasProvider
//     *
//     * @param nodeId
//     * @return
//     */
//    public GasProvider getUnStakingGasProvider(String nodeId) throws IOException, EstimateGasException, NoSupportFunctionType {
//        Function function = createUnStakingFunction(nodeId);
//        return getDefaultGasProvider(function);
//    }
//
//    /**
//     * 撤销质押
//     *
//     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId) {
//        Function function = createUnStakingFunction(nodeId);
//        return executeRemoteCallTransactionStep1(function);
//    }
//
//    /**
//     * 撤销质押
//     *
//     * @param nodeId      64bytes 被质押的节点Id(也叫候选人的节点Id)
//     * @param gasProvider 自定义的gasProvider
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId, GasProvider gasProvider) {
//        Function function = createUnStakingFunction(nodeId);
//        return executeRemoteCallTransactionStep1(function, gasProvider);
//    }
//
//    private Function createUnStakingFunction(String nodeId) {
//        Function function = new Function(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
//                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
//        return function;
//    }
//
//    /**
//     * 更新质押信息
//     *
//     * @param updateStakingParam
//     * @return
//     */
//    public RemoteCall<TransactionResponse> updateStakingInfo(UpdateStakingParam updateStakingParam) {
//        Function function = createUpdateStakingFunction(updateStakingParam);
//        return executeRemoteCallTransaction(function);
//    }
//
//    /**
//     * 更新质押信息
//     *
//     * @param updateStakingParam
//     * @param gasProvider
//     * @return
//     */
//    public RemoteCall<TransactionResponse> updateStakingInfo(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
//        Function function = createUpdateStakingFunction(updateStakingParam);
//        return executeRemoteCallTransaction(function,gasProvider);
//    }
//
//    /**
//     * 获取更新质押信息GasProvider
//     *
//     * @param updateStakingParam
//     * @return
//     */
//    public GasProvider getUpdateStakingInfoGasProvider(UpdateStakingParam updateStakingParam) throws IOException, EstimateGasException, NoSupportFunctionType {
//        Function function = createUpdateStakingFunction(updateStakingParam);
//        return getDefaultGasProvider(function);
//    }
//
//    /**
//     * 更新质押信息
//     *
//     * @param updateStakingParam
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam) {
//        Function function = createUpdateStakingFunction(updateStakingParam);
//        return executeRemoteCallTransactionStep1(function);
//    }
//
//    /**
//     * 更新质押信息
//     *
//     * @param updateStakingParam
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
//        Function function = createUpdateStakingFunction(updateStakingParam);
//        return executeRemoteCallTransactionStep1(function, gasProvider);
//    }
//
//    private Function createUpdateStakingFunction(UpdateStakingParam updateStakingParam) {
//        Function function = new Function(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
//                updateStakingParam.getSubmitInputParameters());
//        return function;
//    }
//
//    /**
//     * 增持质押
//     *
//     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
//     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
//     * @param amount            增持的von
//     * @return
//     */
//    public RemoteCall<TransactionResponse> addStaking(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
//        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
//        return executeRemoteCallTransaction(function);
//    }
//
//    /**
//     * 增持质押
//     *
//     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
//     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
//     * @param amount            增持的von
//     * @param gasProvider
//     * @return
//     */
//    public RemoteCall<TransactionResponse> addStaking(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
//        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
//        return executeRemoteCallTransaction(function, gasProvider);
//    }
//
//    /**
//     * 获取增持质押gasProvider
//     *
//     * @param nodeId
//     * @param stakingAmountType
//     * @param amount
//     * @return
//     */
//    public GasProvider getAddStakingGasProvider(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) throws IOException, EstimateGasException, NoSupportFunctionType {
//        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
//        return getDefaultGasProvider(function);
//    }
//
//    /**
//     * 增持质押
//     *
//     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
//     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
//     * @param amount            增持的von
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> addStakingReturnTransaction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
//        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
//        return executeRemoteCallTransactionStep1(function);
//    }
//
//    /**
//     * 增持质押
//     *
//     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
//     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
//     * @param amount            增持的von
//     * @param gasProvider
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> addStakingReturnTransaction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
//        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount );
//        return executeRemoteCallTransactionStep1(function, gasProvider);
//    }
//
//    private Function createAddStakingFunction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
//        Function function = new Function(FunctionType.ADD_STAKING_FUNC_TYPE,
//                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
//                        new Uint16(stakingAmountType.getValue()),
//                        new Uint256(amount)));
//        return function;
//    }
}
