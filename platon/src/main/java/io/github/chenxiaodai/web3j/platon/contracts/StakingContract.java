package io.github.chenxiaodai.web3j.platon.contracts;

import io.github.chenxiaodai.web3j.platon.contracts.common.Function;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.*;
import io.github.chenxiaodai.web3j.platon.contracts.enums.CreateStakingAmountTypeEnum;
import io.github.chenxiaodai.web3j.platon.contracts.enums.DelegateAmountTypeEnum;
import io.github.chenxiaodai.web3j.platon.contracts.enums.IncreaseStakingAmountTypeEnum;
import io.github.chenxiaodai.web3j.platon.contracts.enums.InnerContractEnum;
import io.github.chenxiaodai.web3j.platon.contracts.type.HexStringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.StringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.Type;
import io.github.chenxiaodai.web3j.platon.contracts.type.UintType;
import io.github.chenxiaodai.web3j.platon.contracts.utils.PPOSFuncUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StakingContract extends BaseContract {

    public final static int FUNC_CREATE_STAKING = 1000;
    public final static int FUNC_EDIT_CANDIDATE = 1001;
    public final static int FUNC_INCREASE_STAKING = 1002;
    public final static int FUNC_WITHDREW_STAKING = 1003;
    public final static int FUNC_DELEGATE = 1004;
    public final static int FUNC_WITHDREW_DELEGATION = 1005;
    public final static int FUNC_REDEEM_DELEGATION = 1006;
    public final static int FUNC_GET_VERIFIER_LIST = 1100;
    public final static int FUNC_GET_VALIDATOR_LIST = 1101;
    public final static int FUNC_GET_CANDIDATE_LIST = 1102;
    public final static int FUNC_GET_RELATED_LIST_BY_DEL_ADDR = 1103;
    public final static int FUNC_GET_DELEGATE_INFO = 1104;
    public final static int FUNC_GET_CANDIDATE_INFO = 1105;
    public final static int FUNC_GET_DELEGATION_LOCK_INFO = 1106;
    public final static int FUNC_GET_PACKAGE_REWARD = 1200;
    public final static int FUNC_GET_STAKING_REWARD = 1201;
    public final static int FUNC_GET_AVG_PACK_TIME = 1202;

	
	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static StakingContract load(Web3j web3j) {
        return new StakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static StakingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new StakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
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
     * @param amount 质押金额
     * @param programVersion  程序的真实版本，治理rpc获取
     * @param programVersionSign 程序的真实版本签名，治理rpc获取
     * @param blsPubKey bls的公钥
     * @param blsProof bls的证明,通过拉取证明接口获取
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> createStaking(String nodeId, CreateStakingAmountTypeEnum type, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details, BigInteger amount, BigInteger programVersion, String programVersionSign, String blsPubKey, String blsProof) {
        return executeRemoteCallTransaction(getFunctionOfCreateStaking(nodeId, type, benefitAddress, rewardPer, externalId, nodeName, website, details, amount, programVersion, programVersionSign, blsPubKey, blsProof));
    }

    public static String encodeTransactionDataOfCreateStaking(String nodeId, CreateStakingAmountTypeEnum type, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details, BigInteger amount, BigInteger programVersion, String programVersionSign, String blsPubKey, String blsProof){
        return PPOSFuncUtils.encode(getFunctionOfCreateStaking(nodeId, type, benefitAddress, rewardPer, externalId, nodeName, website, details, amount, programVersion, programVersionSign, blsPubKey, blsProof));
    }

    private static Function getFunctionOfCreateStaking(String nodeId, CreateStakingAmountTypeEnum type, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details, BigInteger amount, BigInteger programVersion, String programVersionSign, String blsPubKey, String blsProof){
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
        return new Function(FUNC_CREATE_STAKING, param);
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
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> editCandidate(String nodeId, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details) {
        return executeRemoteCallTransaction(getFunctionOfEditCandidate(nodeId, benefitAddress, rewardPer, externalId, nodeName, website, details));
    }

    public static String encodeTransactionDataOfEditCandidate(String nodeId, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details){
        return PPOSFuncUtils.encode(getFunctionOfEditCandidate(nodeId, benefitAddress, rewardPer, externalId, nodeName, website, details));
    }

    private static Function getFunctionOfEditCandidate(String nodeId, String benefitAddress, BigInteger rewardPer, String externalId, String nodeName, String website, String details){
        List<Type> param = Arrays.asList(
                new HexStringType(benefitAddress),
                new HexStringType(nodeId),
                new UintType(rewardPer),
                new StringType(externalId),
                new StringType(nodeName),
                new StringType(website),
                new StringType(details)
        );
        return new Function(FUNC_EDIT_CANDIDATE, param);
    }

    /**
     * 增持质押
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @param type              表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * @param amount            增持的von
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> increaseStaking(String nodeId, IncreaseStakingAmountTypeEnum type, BigInteger amount) {
        return executeRemoteCallTransaction(getFunctionOfIncreaseStaking(nodeId, type, amount));
    }

    public static String encodeTransactionDataOfIncreaseStaking(String nodeId, IncreaseStakingAmountTypeEnum type, BigInteger amount){
        return PPOSFuncUtils.encode(getFunctionOfIncreaseStaking(nodeId, type, amount));
    }

    private static Function getFunctionOfIncreaseStaking(String nodeId, IncreaseStakingAmountTypeEnum type, BigInteger amount){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new UintType(type.getValue()),
                new UintType(amount)
        );
        return new Function(FUNC_INCREASE_STAKING, param);
    }

    /**
     * 撤销质押(一次性发起全部撤销，多次到账)
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> withdrewStaking(String nodeId) {
        return executeRemoteCallTransaction(getFunctionOfWithdrewStaking(nodeId));
    }

    public static String encodeTransactionDataOfWithdrewStaking(String nodeId){
        return PPOSFuncUtils.encode(getFunctionOfWithdrewStaking(nodeId));
    }

    private static Function getFunctionOfWithdrewStaking(String nodeId){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId)
        );
        return new Function(FUNC_WITHDREW_STAKING, param);
    }

    /**
     * 发起委托
     *
     * @param nodeId            委托的节点Id
     * @param type              表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> delegate(String nodeId, DelegateAmountTypeEnum type, BigInteger amount) {
        return executeRemoteCallTransaction(getFunctionOfDelegate(nodeId, type, amount));
    }

    public static String encodeTransactionDataOfDelegate(String nodeId, DelegateAmountTypeEnum type, BigInteger amount){
        return PPOSFuncUtils.encode(getFunctionOfDelegate(nodeId, type, amount));
    }

    private static Function getFunctionOfDelegate(String nodeId, DelegateAmountTypeEnum type, BigInteger amount){
        List<Type> param = Arrays.asList(
                new UintType(type.getValue()),
                new HexStringType(nodeId),
                new UintType(amount)
        );
        return new Function(FUNC_DELEGATE, param);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId            委托的节点Id
     * @param stakingBlockNum   代表着某个node的某次质押的唯一标识
     * @param amount            减持生效的委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> withdrewDelegation(String nodeId, BigInteger stakingBlockNum, BigInteger amount) {
        return executeRemoteCallTransaction(getFunctionOfWithdrewDelegation(nodeId, stakingBlockNum, amount));
    }

    public static String encodeTransactionDataOfWithdrewDelegation(String nodeId, BigInteger stakingBlockNum, BigInteger amount){
        return PPOSFuncUtils.encode(getFunctionOfWithdrewDelegation(nodeId, stakingBlockNum, amount));
    }

    private static Function getFunctionOfWithdrewDelegation(String nodeId, BigInteger stakingBlockNum, BigInteger amount){
        List<Type> param = Arrays.asList(
                new UintType(stakingBlockNum),
                new HexStringType(nodeId),
                new UintType(amount)
        );
        return new Function(FUNC_WITHDREW_DELEGATION, param);
    }

    /**
     * 领取解锁的委托
     *
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> redeemDelegation() {
        return executeRemoteCallTransaction(getFunctionOfRedeemDelegation());
    }

    public static String encodeTransactionDataOfRedeemDelegation(){
        return PPOSFuncUtils.encode(getFunctionOfRedeemDelegation());
    }

    private static Function getFunctionOfRedeemDelegation(){
        return new Function(FUNC_REDEEM_DELEGATION);
    }

    /**
     *  获得领取解锁的委托日志（当减持/撤销委托成功时调用）
     *
     * @param transactionReceipt 交易回执
     * @return 领取详情
     * @throws TransactionException 交易异常
     */
    public RedeemDelegation decodeRedeemDelegateLog(TransactionReceipt transactionReceipt) throws TransactionException {
        List<RlpType> rlpList = decodePPOSLog(transactionReceipt);
        RedeemDelegation result = new RedeemDelegation();
        BigInteger released = ((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(1)).getBytes()).getValues().get(0)).asPositiveBigInteger();
        BigInteger restrictingPlan = ((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(2)).getBytes()).getValues().get(0)).asPositiveBigInteger();
        result.setReleased(released);
        result.setRestrictingPlan(restrictingPlan);
        return result;
    }

    /**
     *  获得解除委托时日志信息（当减持/撤销委托成功时调用）
     *
     * @param transactionReceipt 交易回执
     * @return 解委托详情
     * @throws TransactionException 交易异常
     */
    public UnDelegation decodeUnDelegateLog(TransactionReceipt transactionReceipt) throws TransactionException {
        List<RlpType> rlpList = decodePPOSLog(transactionReceipt);
        UnDelegation result = new UnDelegation();
        BigInteger delegateIncome = ((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(1)).getBytes()).getValues().get(0)).asPositiveBigInteger();
        result.setDelegateIncome(delegateIncome);
        if(rlpList.size() > 2){
            BigInteger released = ((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(2)).getBytes()).getValues().get(0)).asPositiveBigInteger();
            BigInteger restrictingPlan = ((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(3)).getBytes()).getValues().get(0)).asPositiveBigInteger();
            BigInteger lockReleased = ((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(4)).getBytes()).getValues().get(0)).asPositiveBigInteger();
            BigInteger lockRestrictingPlan =((RlpString) RlpDecoder.decode(((RlpString)rlpList.get(5)).getBytes()).getValues().get(0)).asPositiveBigInteger();
            result.setReleased(Optional.of(released));
            result.setRestrictingPlan(Optional.of(restrictingPlan));
            result.setLockReleased(Optional.of(lockReleased));
            result.setLockRestrictingPlan(Optional.of(lockRestrictingPlan));
        }
        return result;
    }

    /**
     * 查询当前结算周期的验证人队列
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<VerifierNode>>> getVerifierList() {
        return executeRemoteCallListValueReturn(getFunctionOfGetVerifierList(), VerifierNode.class);
    }

    public static String encodeTransactionDataOfGetVerifierList(){
        return PPOSFuncUtils.encode(getFunctionOfGetVerifierList());
    }

    private static Function getFunctionOfGetVerifierList(){
        return new Function(FUNC_GET_VERIFIER_LIST);
    }

    /**
     * 查询当前共识周期的验证人列表
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<VerifierNode>>> getValidatorList() {
        return executeRemoteCallListValueReturn(getFunctionOfGetValidatorList(), VerifierNode.class);
    }

    public static String encodeTransactionDataOfGetValidatorList(){
        return PPOSFuncUtils.encode(getFunctionOfGetValidatorList());
    }

    private static Function getFunctionOfGetValidatorList(){
        return new Function(FUNC_GET_VALIDATOR_LIST);
    }

    /**
     * 查询所有实时的候选人列表
     *
     * @return 候选人列表
     */
    public RemoteCall<CallResponse<List<CandidateNode>>> getCandidateList() {
        return executeRemoteCallListValueReturn(getFunctionOfGetCandidateList(), CandidateNode.class);
    }

    public static String encodeTransactionDataOfGetCandidateList(){
        return PPOSFuncUtils.encode(getFunctionOfGetCandidateList());
    }

    private static Function getFunctionOfGetCandidateList(){
        return new Function(FUNC_GET_CANDIDATE_LIST);
    }

    /**
     * 查询当前账户地址所委托的节点的NodeID和质押Id
     *
     * @param address 委托人的账户地址
     * @return 用户委托关系
     */
    public RemoteCall<CallResponse<List<DelegationIdInfo>>> getRelatedListByDelAddr(String address) {
        return executeRemoteCallListValueReturn(getFunctionOfGetRelatedListByDelAddr(address), DelegationIdInfo.class);
    }

    public static String encodeTransactionDataOfGetRelatedListByDelAddr(String address){
        return PPOSFuncUtils.encode(getFunctionOfGetRelatedListByDelAddr(address));
    }

    private static Function getFunctionOfGetRelatedListByDelAddr(String address){
        List<Type> param = Arrays.asList(
                new HexStringType(address)
        );
        return new Function(FUNC_GET_RELATED_LIST_BY_DEL_ADDR, param);
    }

    /**
     * 查询当前单个节点的委托信息
     *
     * @param nodeId 验证人的节点Id
     * @param stakingBlockNum 发起质押时的区块高度
     * @param delAddr 委托人账户地址
     * @return 委托详情
     */
    public RemoteCall<CallResponse<Delegation>> getDelegateInfo(String nodeId, BigInteger stakingBlockNum, String delAddr) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetDelegateInfo(nodeId, stakingBlockNum, delAddr), Delegation.class);
    }

    public static String encodeTransactionDataOfGetDelegateInfo(String nodeId, BigInteger stakingBlockNum, String delAddr){
        return PPOSFuncUtils.encode(getFunctionOfGetDelegateInfo(nodeId, stakingBlockNum, delAddr));
    }

    private static Function getFunctionOfGetDelegateInfo(String nodeId, BigInteger stakingBlockNum, String delAddr){
        List<Type> param = Arrays.asList(
                new UintType(stakingBlockNum),
                new HexStringType(delAddr),
                new HexStringType(nodeId)
        );
        return new Function(FUNC_GET_DELEGATE_INFO, param);
    }

    /**
     * 获取质押信息
     *
     * @param nodeId 节点id
     * @return 质押详情
     */
    public RemoteCall<CallResponse<CandidateNode>> getStakingInfo(String nodeId) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetStakingInfo(nodeId), CandidateNode.class);
    }

    public static String encodeTransactionDataOfGetStakingInfo(String nodeId){
        return PPOSFuncUtils.encode(getFunctionOfGetStakingInfo(nodeId));
    }

    private static Function getFunctionOfGetStakingInfo(String nodeId){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId)
        );
        return new Function(FUNC_GET_CANDIDATE_INFO, param);
    }

    /**
     * 查询账户处于锁定期与解锁期的委托信息
     *
     * @param delAddr 委托人账户地址
     * @return 委托锁定信息
     */
    public RemoteCall<CallResponse<DelegationLockInfo>> getDelegationLockInfo(String delAddr) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetDelegationLockInfo(delAddr), DelegationLockInfo.class);
    }

    public static String encodeTransactionDataOfGetDelegationLockInfo(String delAddr){
        return PPOSFuncUtils.encode(getFunctionOfGetDelegationLockInfo(delAddr));
    }

    private static Function getFunctionOfGetDelegationLockInfo(String delAddr){
        List<Type> param = Arrays.asList(
                new HexStringType(delAddr)
        );
        return new Function(FUNC_GET_DELEGATION_LOCK_INFO, param);
    }

    /**
     * 查询当前结算周期的区块奖励
     *
     * @return 区块奖励
     */
    public RemoteCall<CallResponse<BigInteger>> getPackageReward() {
        Function function = new Function(FUNC_GET_PACKAGE_REWARD);
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static String encodeTransactionDataOfGetPackageReward(){
        return PPOSFuncUtils.encode(getFunctionOfGetPackageReward());
    }

    private static Function getFunctionOfGetPackageReward(){
        return new Function(FUNC_GET_PACKAGE_REWARD);
    }

    /**
     * 查询当前结算周期的质押奖励
     *
     * @return 质押奖励
     */
    public RemoteCall<CallResponse<BigInteger>> getStakingReward() {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetStakingReward(), BigInteger.class);
    }

    public static String encodeTransactionDataOfGetStakingReward(){
        return PPOSFuncUtils.encode(getFunctionOfGetStakingReward());
    }

    private static Function getFunctionOfGetStakingReward(){
        return new Function(FUNC_GET_STAKING_REWARD);
    }

    /**
     * 查询当前结算周期的质押奖励
     *
     * @return 平均出块间隔
     */
    public RemoteCall<CallResponse<Integer>> getAvgPackTime() {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetAvgPackTime(), Integer.class);
    }

    public static String encodeTransactionDataOfGetAvgPackTime(){
        return PPOSFuncUtils.encode(getFunctionOfGetAvgPackTime());
    }

    private static Function getFunctionOfGetAvgPackTime(){
        return new Function(FUNC_GET_AVG_PACK_TIME);
    }
}
