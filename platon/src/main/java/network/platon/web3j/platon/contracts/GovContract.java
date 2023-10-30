package network.platon.web3j.platon.contracts;

import network.platon.web3j.platon.contracts.common.Function;
import network.platon.web3j.platon.contracts.dto.CallResponse;
import network.platon.web3j.platon.contracts.dto.TransactionResponse;
import network.platon.web3j.platon.contracts.dto.resp.GovernParam;
import network.platon.web3j.platon.contracts.dto.resp.Proposal;
import network.platon.web3j.platon.contracts.dto.resp.TallyResult;
import network.platon.web3j.platon.contracts.enums.InnerContractEnum;
import network.platon.web3j.platon.contracts.enums.VoteOptionEnum;
import network.platon.web3j.platon.contracts.type.HexStringType;
import network.platon.web3j.platon.contracts.type.StringType;
import network.platon.web3j.platon.contracts.type.Type;
import network.platon.web3j.platon.contracts.type.UintType;
import network.platon.web3j.platon.contracts.utils.PPOSFuncUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class GovContract extends BaseContract {

    public final static int FUNC_SUBMIT_TEXT = 2000;
    public final static int FUNC_SUBMIT_VERSION = 2001;
    public final static int FUNC_SUBMIT_PARAM = 2002;
    public final static int FUNC_VOTE = 2003;
    public final static int FUNC_DECLARE_VERSION = 2004;
    public final static int FUNC_SUBMIT_CANCEL = 2005;
    public final static int FUNC_GET_PROPOSAL = 2100;
    public final static int FUNC_GET_TALLY_RESULT = 2101;
    public final static int FUNC_LIST_PROPOSAL = 2102;
    public final static int FUNC_GET_ACTIVE_VERSION = 2103;
    public final static int FUNC_GET_GOVERN_PARAM_VALUE = 2104;
    public final static int FUNC_GET_ACCUMULATION_VERIFIERS_COUNT = 2105;
    public final static int FUNC_LIST_GOVERN_PARAM = 2106;


	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static GovContract load(Web3j web3j) {
        return new GovContract(InnerContractEnum.GOV_CONTRACT.getAddress(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static GovContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new GovContract(InnerContractEnum.GOV_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
     */
    public static GovContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new GovContract(InnerContractEnum.GOV_CONTRACT.getAddress(), web3j, credentials, chainId);
    }

    protected GovContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    protected GovContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    protected GovContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 提交文本提案
     *
     * @param nodeId 提交提案的验证人
     * @param pipId 提案id
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> submitText(String nodeId, String pipId) {
        return executeRemoteCallTransaction(getFunctionOfSubmitText(nodeId, pipId));
    }

    public static String encodeTransactionDataOfSubmitText(String nodeId, String pipId){
        return PPOSFuncUtils.encode(getFunctionOfSubmitText(nodeId, pipId));
    }

    private static Function getFunctionOfSubmitText(String nodeId, String pipId){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId)
        );
        return new Function(FUNC_SUBMIT_TEXT, param);
    }


    /**
     * 提交升级提案
     *
     * @param nodeId 提交提案的验证人
     * @param pipId 提案id
     * @param newVersion 升级版本
     * @param endVotingRounds 投票共识轮数量
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> submitVersion(String nodeId, String pipId, BigInteger newVersion, BigInteger endVotingRounds) {
        return executeRemoteCallTransaction(getFunctionOfSubmitVersion(nodeId, pipId, newVersion, endVotingRounds));
    }

    public static String encodeTransactionDataOfSubmitVersion(String nodeId, String pipId, BigInteger newVersion, BigInteger endVotingRounds){
        return PPOSFuncUtils.encode(getFunctionOfSubmitVersion(nodeId, pipId, newVersion, endVotingRounds));
    }

    private static Function getFunctionOfSubmitVersion(String nodeId, String pipId, BigInteger newVersion, BigInteger endVotingRounds){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId),
                new UintType(newVersion),
                new UintType(endVotingRounds)
        );
        return new Function(FUNC_SUBMIT_VERSION, param);
    }

    /**
     * 提交参数提案
     *
     * @param nodeId 提交提案的验证人
     * @param pipId 提案id
     * @param module 参数模块
     * @param name  参数名称
     * @param newValue 参数新值
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> submitParam(String nodeId, String pipId, String module, String name, String newValue) {
        return executeRemoteCallTransaction(getFunctionOfSubmitParam(nodeId, pipId, module, name, newValue));
    }

    public static String encodeTransactionDataOfSubmitParam(String nodeId, String pipId, String module, String name, String newValue){
        return PPOSFuncUtils.encode(getFunctionOfSubmitParam(nodeId, pipId, module, name, newValue));
    }

    private static Function getFunctionOfSubmitParam(String nodeId, String pipId, String module, String name, String newValue){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId),
                new StringType(module),
                new StringType(name),
                new StringType(newValue)
        );
        return new Function(FUNC_SUBMIT_PARAM, param);
    }

    /**
     * 提交取消提案
     *
     * @param nodeId 提交提案的验证人
     * @param pipId 提案id
     * @param endVotingRounds 投票共识轮数量
     * @param tobeCanceledProposalId  待取消的升级提案ID
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> submitCancel(String nodeId, String pipId, BigInteger endVotingRounds, String tobeCanceledProposalId) {
        return executeRemoteCallTransaction(getFunctionOfSubmitCancel(nodeId, pipId, endVotingRounds, tobeCanceledProposalId));
    }

    public static String encodeTransactionDataOfSubmitCancel(String nodeId, String pipId, BigInteger endVotingRounds, String tobeCanceledProposalId){
        return PPOSFuncUtils.encode(getFunctionOfSubmitCancel(nodeId, pipId, endVotingRounds, tobeCanceledProposalId));
    }

    private static Function getFunctionOfSubmitCancel(String nodeId, String pipId, BigInteger endVotingRounds, String tobeCanceledProposalId){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId),
                new UintType(endVotingRounds),
                new HexStringType(tobeCanceledProposalId)
        );
        return new Function(FUNC_SUBMIT_CANCEL, param);
    }


    /**
     * 给提案投票
     *
     * @param nodeId 投票验证人
     * @param proposalId 提案id
     * @param option 投票选项
     * @param programVersion  节点代码版本
     * @param versionSign 代码版本签名
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> vote(String nodeId, String proposalId, VoteOptionEnum option, BigInteger programVersion, String versionSign) {
        return executeRemoteCallTransaction(getFunctionOfVote(nodeId, proposalId, option, programVersion, versionSign));
    }

    public static String encodeTransactionDataOfVote(String nodeId, String proposalId, VoteOptionEnum option, BigInteger programVersion, String versionSign){
        return PPOSFuncUtils.encode(getFunctionOfVote(nodeId, proposalId, option, programVersion, versionSign));
    }

    private static Function getFunctionOfVote(String nodeId, String proposalId, VoteOptionEnum option, BigInteger programVersion, String versionSign){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new HexStringType(proposalId),
                new UintType(option.getValue()),
                new UintType(programVersion),
                new HexStringType(versionSign)
        );
        return new Function(FUNC_VOTE, param);
    }

    /**
     * 版本声明
     *
     * @param nodeId 投票验证人
     * @param programVersion  节点代码版本
     * @param versionSign 代码版本签名
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> declareVersion(String nodeId, BigInteger programVersion, String versionSign) {
        return executeRemoteCallTransaction(getFunctionOfDeclareVersion(nodeId, programVersion, versionSign));
    }

    public static String encodeTransactionDataOfDeclareVersion(String nodeId, BigInteger programVersion, String versionSign){
        return PPOSFuncUtils.encode(getFunctionOfDeclareVersion(nodeId, programVersion, versionSign));
    }

    private static Function getFunctionOfDeclareVersion(String nodeId, BigInteger programVersion, String versionSign){
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new UintType(programVersion),
                new HexStringType(versionSign)
        );
        return new Function(FUNC_DECLARE_VERSION, param);
    }

    /**
     * 查询提案
     *
     * @param proposalId 提案ID
     * @return 提案详情
     */
    public RemoteCall<CallResponse<Proposal>> getProposal(String proposalId) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetProposal(proposalId), Proposal.class);
    }

    public static String encodeTransactionDataOfGetProposal(String proposalId){
        return PPOSFuncUtils.encode(getFunctionOfGetProposal(proposalId));
    }

    private static Function getFunctionOfGetProposal(String proposalId){
        List<Type> param = Arrays.asList(
                new HexStringType(proposalId)
        );
        return new Function(FUNC_GET_PROPOSAL, param);
    }

    /**
     * 查询提案结果
     *
     * @param proposalId 提案ID
     * @return 提案详情
     */
    public RemoteCall<CallResponse<TallyResult>> getTallyResult(String proposalId) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetTallyResult(proposalId), TallyResult.class);
    }

    public static String encodeTransactionDataOfGetTallyResult(String proposalId){
        return PPOSFuncUtils.encode(getFunctionOfGetTallyResult(proposalId));
    }

    private static Function getFunctionOfGetTallyResult(String proposalId){
        List<Type> param = Arrays.asList(
                new HexStringType(proposalId)
        );
        return new Function(FUNC_GET_TALLY_RESULT, param);
    }

    /**
     * 查询提案列表
     *
     * @return 提案列表
     */
    public RemoteCall<CallResponse<List<Proposal>>> listProposal() {
        return executeRemoteCallListValueReturn(getFunctionOfListProposal(), Proposal.class);
    }

    public static String encodeTransactionDataOfListProposal(){
        return PPOSFuncUtils.encode(getFunctionOfListProposal());
    }

    private static Function getFunctionOfListProposal(){
        return new Function(FUNC_LIST_PROPOSAL);
    }

    /**
     * 查询节点的链生效版本
     *
     * @return 版本号
     */
    public RemoteCall<CallResponse<Long>> getActiveVersion() {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetActiveVersion(), Long.class);
    }

    public static String encodeTransactionDataOfGetActiveVersion(){
        return PPOSFuncUtils.encode(getFunctionOfGetActiveVersion());
    }

    private static Function getFunctionOfGetActiveVersion(){
        return new Function(FUNC_GET_ACTIVE_VERSION);
    }

    /**
     * 查询当前块高的治理参数值
     *
     * @param module 参数模块
     * @param name 参数名称
     * @return 版本号
     */
    public RemoteCall<CallResponse<String>> getGovernParamValue(String module, String name) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetGovernParamValue(module, name), String.class);
    }

    public static String encodeTransactionDataOfGetGovernParamValue(String module, String name){
        return PPOSFuncUtils.encode(getFunctionOfGetGovernParamValue(module, name));
    }

    private static Function getFunctionOfGetGovernParamValue(String module, String name){
        List<Type> param = Arrays.asList(
                new StringType(module),
                new StringType(name)
        );
        return new Function(FUNC_GET_GOVERN_PARAM_VALUE, param);
    }

    /**
     * 查询提案的累积可投票人数
     *
     * @param proposalId 提案ID
     * @param blockHash 块hash
     * @return 版本号
     */
    public RemoteCall<CallResponse<List<Integer>>> getAccumulationVerifiersCount(String proposalId, String blockHash) {
        return executeRemoteCallListValueReturn(getFunctionOfGetAccumulationVerifiersCount(proposalId, blockHash), Integer.class);
    }

    public static String encodeTransactionDataOfGetAccumulationVerifiersCount(String proposalId, String blockHash){
        return PPOSFuncUtils.encode(getFunctionOfGetAccumulationVerifiersCount(proposalId, blockHash));
    }

    private static Function getFunctionOfGetAccumulationVerifiersCount(String proposalId, String blockHash){
        List<Type> param = Arrays.asList(
                new HexStringType(proposalId),
                new HexStringType(blockHash)
        );
        return new Function(FUNC_GET_ACCUMULATION_VERIFIERS_COUNT, param);
    }


    /**
     * 查询治理参数列表
     *
     * @param module 模块名
     * @return 版本号
     */
    public RemoteCall<CallResponse<List<GovernParam>>> listGovernParam(String module) {
        return executeRemoteCallListValueReturn(getFunctionOfListGovernParam(module), GovernParam.class);
    }

    public static String encodeTransactionDataOfListGovernParam(String module){
        return PPOSFuncUtils.encode(getFunctionOfListGovernParam(module));
    }

    private static Function getFunctionOfListGovernParam(String module){
        List<Type> param = Arrays.asList(
                new StringType(module)
        );
        return new Function(FUNC_LIST_GOVERN_PARAM, param);
    }

}
