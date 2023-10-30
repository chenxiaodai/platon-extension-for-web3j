package network.platon.web3j.splaton.contracts;

import network.platon.web3j.platon.contracts.StakingContract;
import network.platon.web3j.platon.contracts.common.Function;
import network.platon.web3j.platon.contracts.dto.CallResponse;
import network.platon.web3j.platon.contracts.dto.resp.VerifierNode;
import network.platon.web3j.platon.contracts.enums.InnerContractEnum;
import network.platon.web3j.platon.contracts.type.Type;
import network.platon.web3j.platon.contracts.type.UintType;
import network.platon.web3j.platon.contracts.utils.PPOSFuncUtils;
import network.platon.web3j.splaton.contracts.dto.resp.EpochInfo;
import network.platon.web3j.splaton.contracts.dto.resp.HistoryLowRateSlash;
import network.platon.web3j.splaton.contracts.dto.resp.NodeVersion;
import network.platon.web3j.splaton.contracts.dto.resp.PPosInvokeContractInput;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class SPlatONStakingContract extends StakingContract {

    public final static int FUNC_GET_HISTORY_VERIFIER_LIST = 1300;
    public final static int FUNC_GET_HISTORY_VALIDATOR_LIST = 1301;
    public final static int FUNC_QUERY_NODE_VERSION = 1302;
    public final static int FUNC_QUERY_EPOCH_INFO = 1303;
    public final static int FUNC_GET_HISTORY_SLASH_LIST = 1304;
    public final static int FUNC_GET_HISTORY_TRANS_LIST = 1305;


	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static SPlatONStakingContract load(Web3j web3j) {
        return new SPlatONStakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static SPlatONStakingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new SPlatONStakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
     */
    public static SPlatONStakingContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new SPlatONStakingContract(InnerContractEnum.STAKING_CONTRACT.getAddress(), web3j, credentials, chainId);
    }

    private SPlatONStakingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private SPlatONStakingContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    private SPlatONStakingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 查询历史结算周期的验证人队列
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<VerifierNode>>> getHistoryVerifierList(BigInteger blockNumber) {
        return executeRemoteCallListValueReturn(getFunctionOfGetHistoryVerifierList(blockNumber), VerifierNode.class);
    }
    public static String encodeTransactionDataOfQueryHistoryVerifierList(BigInteger blockNumber){
        return PPOSFuncUtils.encode(getFunctionOfGetHistoryVerifierList(blockNumber));
    }

    private static Function getFunctionOfGetHistoryVerifierList(BigInteger blockNumber){
        List<Type> param = Arrays.asList(
                new UintType(blockNumber)
        );
        return new Function(FUNC_GET_HISTORY_VERIFIER_LIST, param);
    }

    /**
     * 查询当前共识周期的验证人列表
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<VerifierNode>>> getHistoryValidatorList(BigInteger blockNumber) {
        return executeRemoteCallListValueReturn(getFunctionOfGetHistoryValidatorList(blockNumber), VerifierNode.class);
    }
    public static String encodeTransactionDataOfQueryHistoryValidatorList(BigInteger blockNumber){
        return PPOSFuncUtils.encode(getFunctionOfGetHistoryValidatorList(blockNumber));
    }

    private static Function getFunctionOfGetHistoryValidatorList(BigInteger blockNumber){
        List<Type> param = Arrays.asList(
                new UintType(blockNumber)
        );
        return new Function(FUNC_GET_HISTORY_VALIDATOR_LIST, param);
    }


    /**
     * 查询当前共识周期的验证人列表
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<NodeVersion>>> getNodeVersionList() {
        return executeRemoteCallListValueReturn(getFunctionOfGetNodeVersionList(), NodeVersion.class);
    }
    public static String encodeTransactionDataOfGetNodeVersionList(){
        return PPOSFuncUtils.encode(getFunctionOfGetNodeVersionList());
    }
    private static Function getFunctionOfGetNodeVersionList(){
        return new Function(FUNC_QUERY_NODE_VERSION);
    }

    /**
     * 查询结算周期信息
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<EpochInfo>> getEpochInfo(BigInteger blockNumber) {
        return executeRemoteCallSingleValueReturn(getFunctionOfGetEpochInfo(blockNumber), EpochInfo.class);
    }
    public static String encodeTransactionDataOfGetEpochInfo(BigInteger blockNumber){
        return PPOSFuncUtils.encode(getFunctionOfGetEpochInfo(blockNumber));
    }
    private static Function getFunctionOfGetEpochInfo(BigInteger blockNumber){
        List<Type> param = Arrays.asList(
                new UintType(blockNumber)
        );
        return new Function(FUNC_QUERY_EPOCH_INFO, param);
    }


    /**
     * 查询惩罚的节点列表
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<HistoryLowRateSlash>>> getHistorySlashList(BigInteger blockNumber) {
        return executeRemoteCallListValueReturn(getFunctionOfGetHistorySlashList(blockNumber), HistoryLowRateSlash.class);
    }
    public static String encodeTransactionDataOfGetHistorySlashList(BigInteger blockNumber){
        return PPOSFuncUtils.encode(getFunctionOfGetHistorySlashList(blockNumber));
    }
    private static Function getFunctionOfGetHistorySlashList(BigInteger blockNumber){
        List<Type> param = Arrays.asList(
                new UintType(blockNumber)
        );
        return new Function(FUNC_GET_HISTORY_SLASH_LIST, param);
    }


    /**
     * 查询内置合约调用列表
     *
     * @return 验证人列表
     */
    public RemoteCall<CallResponse<List<PPosInvokeContractInput>>> getInnerContractCallList(BigInteger blockNumber) {
        return executeRemoteCallListValueReturn(getFunctionOfGetInnerContractCallList(blockNumber), PPosInvokeContractInput.class);
    }
    public static String encodeTransactionDataOfGetInnerContractCallList(BigInteger blockNumber){
        return PPOSFuncUtils.encode(getFunctionOfGetInnerContractCallList(blockNumber));
    }
    private static Function getFunctionOfGetInnerContractCallList(BigInteger blockNumber){
        List<Type> param = Arrays.asList(
                new UintType(blockNumber)
        );
        return new Function(FUNC_GET_HISTORY_TRANS_LIST, param);
    }
}
