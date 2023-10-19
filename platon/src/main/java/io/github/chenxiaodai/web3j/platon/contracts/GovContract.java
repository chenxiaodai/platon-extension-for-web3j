package io.github.chenxiaodai.web3j.platon.contracts;

import io.github.chenxiaodai.web3j.platon.contracts.common.Function;
import io.github.chenxiaodai.web3j.platon.contracts.dto.CallResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.TransactionResponse;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.GovernParam;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.Proposal;
import io.github.chenxiaodai.web3j.platon.contracts.dto.resp.TallyResult;
import io.github.chenxiaodai.web3j.platon.contracts.enums.InnerContractEnum;
import io.github.chenxiaodai.web3j.platon.contracts.enums.VoteOptionEnum;
import io.github.chenxiaodai.web3j.platon.contracts.type.HexStringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.StringType;
import io.github.chenxiaodai.web3j.platon.contracts.type.Type;
import io.github.chenxiaodai.web3j.platon.contracts.type.UintType;
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

    private GovContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private GovContract(String contractAddress, Web3j web3j, Credentials credentials, long chainId) {
        super(contractAddress, web3j, credentials, chainId);
    }

    private GovContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
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
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId)
        );
        Function function = new Function(FUNC_SUBMIT_TEXT, param);
        return executeRemoteCallTransaction(function);
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
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId),
                new UintType(newVersion),
                new UintType(endVotingRounds)
        );
        Function function = new Function(FUNC_SUBMIT_VERSION, param);
        return executeRemoteCallTransaction(function);
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
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId),
                new StringType(module),
                new StringType(name),
                new StringType(newValue)
        );
        Function function = new Function(FUNC_SUBMIT_PARAM, param);
        return executeRemoteCallTransaction(function);
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
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new StringType(pipId),
                new UintType(endVotingRounds),
                new HexStringType(tobeCanceledProposalId)
        );
        Function function = new Function(FUNC_SUBMIT_CANCEL, param);
        return executeRemoteCallTransaction(function);
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
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new HexStringType(proposalId),
                new UintType(option.getValue()),
                new UintType(programVersion),
                new HexStringType(versionSign)
        );
        Function function = new Function(FUNC_VOTE, param);
        return executeRemoteCallTransaction(function);
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
        List<Type> param = Arrays.asList(
                new HexStringType(nodeId),
                new UintType(programVersion),
                new HexStringType(versionSign)
        );
        Function function = new Function(FUNC_DECLARE_VERSION, param);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 查询提案
     *
     * @param proposalId 提案ID
     * @return 提案详情
     */
    public RemoteCall<CallResponse<Proposal>> getProposal(String proposalId) {
        List<Type> param = Arrays.asList(
                new HexStringType(proposalId)
        );
        Function function = new Function(FUNC_GET_PROPOSAL, param);
        return executeRemoteCallSingleValueReturn(function, Proposal.class);
    }

    /**
     * 查询提案结果
     *
     * @param proposalId 提案ID
     * @return 提案详情
     */
    public RemoteCall<CallResponse<TallyResult>> getTallyResult(String proposalId) {
        List<Type> param = Arrays.asList(
                new HexStringType(proposalId)
        );
        Function function = new Function(FUNC_GET_TALLY_RESULT, param);
        return executeRemoteCallSingleValueReturn(function, TallyResult.class);
    }

    /**
     * 查询提案列表
     *
     * @return 提案列表
     */
    public RemoteCall<CallResponse<List<Proposal>>> listProposal() {
        Function function = new Function(FUNC_LIST_PROPOSAL);
        return executeRemoteCallListValueReturn(function, Proposal.class);
    }

    /**
     * 查询节点的链生效版本
     *
     * @return 版本号
     */
    public RemoteCall<CallResponse<Long>> getActiveVersion() {
        Function function = new Function(FUNC_GET_ACTIVE_VERSION);
        return executeRemoteCallSingleValueReturn(function, Long.class);
    }

    /**
     * 查询当前块高的治理参数值
     *
     * @param module 参数模块
     * @param name 参数名称
     * @return 版本号
     */
    public RemoteCall<CallResponse<String>> getGovernParamValue(String module, String name) {
        List<Type> param = Arrays.asList(
                new StringType(module),
                new StringType(name)
        );
        Function function = new Function(FUNC_GET_GOVERN_PARAM_VALUE, param);
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    /**
     * 查询提案的累积可投票人数
     *
     * @param proposalId 提案ID
     * @param blockHash 块hash
     * @return 版本号
     */
    public RemoteCall<CallResponse<List<Integer>>> getAccumulationVerifiersCount(String proposalId, String blockHash) {
        List<Type> param = Arrays.asList(
                new HexStringType(proposalId),
                new HexStringType(blockHash)
        );
        Function function = new Function(FUNC_GET_ACCUMULATION_VERIFIERS_COUNT, param);
        return executeRemoteCallListValueReturn(function, Integer.class);
    }


    /**
     * 查询治理参数列表
     *
     * @param module 模块名
     * @return 版本号
     */
    public RemoteCall<CallResponse<List<GovernParam>>> listGovernParam(String module) {
        List<Type> param = Arrays.asList(
                new StringType(module)
        );
        Function function = new Function(FUNC_LIST_GOVERN_PARAM, param);
        return executeRemoteCallListValueReturn(function, GovernParam.class);
    }


}
