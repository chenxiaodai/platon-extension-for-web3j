package network.platon.web3j.platon.contracts;

import network.platon.web3j.platon.contracts.common.Function;
import network.platon.web3j.platon.contracts.dto.CallResponse;
import network.platon.web3j.platon.contracts.dto.TransactionResponse;
import network.platon.web3j.platon.contracts.enums.DuplicateSignTypeEnum;
import network.platon.web3j.platon.contracts.enums.InnerContractEnum;
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

public class SlashingContract extends BaseContract {

    public final static int FUNC_REPORT_DUPLICATE_SIGN = 3000;
    public final static int FUNC_CHECK_DUPLICATE_SIGN = 3001;


	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j 客户端
	 * @return staking合约
	 */
    public static SlashingContract load(Web3j web3j) {
        return new SlashingContract(InnerContractEnum.SLASHING_CONTRACT.getAddress(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j 客户端
     * @param transactionManager 交易管理
     * @return staking合约
     */
    public static SlashingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new SlashingContract(InnerContractEnum.SLASHING_CONTRACT.getAddress(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j 客户端
     * @param credentials 钱包
     * @param chainId 链id
     * @return staking合约
     */
    public static SlashingContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new SlashingContract(InnerContractEnum.SLASHING_CONTRACT.getAddress(), web3j, credentials, chainId);
    }

    private SlashingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private SlashingContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    private SlashingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 举报双签
     *
     * @param type 代表双签类型，1：prepareBlock，2：prepareVote，3：viewChange
     * @param data 单个证据的json值
     * @return 返回交易回执信息
     */
    public RemoteCall<TransactionResponse> reportDuplicateSign(DuplicateSignTypeEnum type, String data) {
        return executeRemoteCallTransaction(getFunctionOfReportDuplicateSign(type ,data));
    }

    public static String encodeTransactionDataOfReportDuplicateSign(DuplicateSignTypeEnum type, String data){
        return PPOSFuncUtils.encode(getFunctionOfReportDuplicateSign(type ,data));
    }

    private static Function getFunctionOfReportDuplicateSign(DuplicateSignTypeEnum type, String data){
        List<Type> param = Arrays.asList(
                new UintType(type.getValue()),
                new StringType(data)
        );
        return new Function(FUNC_REPORT_DUPLICATE_SIGN, param);
    }


    /**
     * 查询节点是否已被举报过多签
     *
     * @param type 代表双签类型
     * @param nodeId 举报的节点Id
     * @param blockNumber 多签的块高
     * @return 提案详情
     */
    public RemoteCall<CallResponse<String>> checkDuplicateSign(DuplicateSignTypeEnum type, String nodeId, BigInteger blockNumber) {
        return executeRemoteCallSingleValueReturn(getFunctionOfCheckDuplicateSign(type ,nodeId, blockNumber), String.class);
    }

    public static String encodeTransactionDataOfCheckDuplicateSign(DuplicateSignTypeEnum type, String nodeId, BigInteger blockNumber){
        return PPOSFuncUtils.encode(getFunctionOfCheckDuplicateSign(type ,nodeId, blockNumber));
    }

    private static Function getFunctionOfCheckDuplicateSign(DuplicateSignTypeEnum type, String nodeId, BigInteger blockNumber){
        List<Type> param = Arrays.asList(
                new UintType(type.getValue()),
                new HexStringType(nodeId),
                new UintType(blockNumber)
        );
        return new Function(FUNC_CHECK_DUPLICATE_SIGN, param);
    }
}
