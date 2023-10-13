package io.github.chenxiaodai.web3j.platon.utils;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.rlp.RlpList;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: dongqile
 * Date: 2019/1/10
 * Time: 10:52
 */
public class NodeIdUtils {

    /**
     * 通过区块计算节点公钥
     *
     * @param block 区块信息
     * @return 返回节点id
     */
    public static String getNodeIdFromBlock(EthBlock.Block block) {
        String publicKey = recoverFromBlock(block).toString(16);
        // 不足128前面补0
        int lack = 128 - publicKey.length();
        if (lack <= 0) return publicKey;
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < lack; i++) prefix.append("0");
        prefix.append(publicKey);
        return prefix.toString();
    }

    private static BigInteger recoverFromBlock(EthBlock.Block block) {
        String extraData = block.getExtraData();
        String signature = extraData.substring(66, extraData.length());
        byte[] msgHash = getMsgHash(block);
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        byte[] r = Arrays.copyOfRange(signatureBytes, 0, 32);
        byte[] s = Arrays.copyOfRange(signatureBytes, 32, 64);
        return Sign.recoverFromSignature(v, new ECDSASignature(new BigInteger(1, r), new BigInteger(1, s)), msgHash);
    }

    private static byte[] getMsgHash(EthBlock.Block block) {
        byte[] signData = encode(block);
        return Hash.sha3(signData);
    }

    private static byte[] encode(EthBlock.Block block) {
        List<RlpType> values = asRlpValues(block);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    private static List<RlpType> asRlpValues(EthBlock.Block block) {
        List<RlpType> result = new ArrayList<>();
        result.add(RlpString.create(decodeHash(block.getParentHash())));
        result.add(RlpString.create(decodeHash(block.getMiner())));
        result.add(RlpString.create(decodeHash(block.getStateRoot())));
        result.add(RlpString.create(decodeHash(block.getTransactionsRoot())));
        result.add(RlpString.create(decodeHash(block.getReceiptsRoot())));
        result.add(RlpString.create(decodeHash(block.getLogsBloom())));
        result.add(RlpString.create(block.getNumber()));
        result.add(RlpString.create(block.getGasLimit()));
        result.add(RlpString.create(block.getGasUsed()));
        result.add(RlpString.create(block.getTimestamp()));
        result.add(RlpString.create(decodeHash(block.getExtraData().substring(0, 66))));
        result.add(RlpString.create(decodeHash(block.getNonceRaw())));
        return result;
    }

    private static byte[] decodeHash(String hex) {
        return Hex.decode(Numeric.cleanHexPrefix(hex));
    }
}
