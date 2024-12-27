package network.platon.web3j.platon.contracts.utils;

import network.platon.web3j.platon.contracts.enums.CreateStakingAmountTypeEnum;
import network.platon.web3j.platon.contracts.enums.IncreaseStakingAmountTypeEnum;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.rlp.*;
import org.web3j.tuples.generated.Tuple13;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PPOSCodecUtils {

    public static Tuple3<String, IncreaseStakingAmountTypeEnum, BigInteger> decodeInputOfIncreaseStaking(String input) {
        RlpList rootList = (RlpList) RlpDecoder.decode(Hex.decode(input.replace("0x", ""))).getValues().get(0);
        String nodeId = getHex(rootList, 1);
        IncreaseStakingAmountTypeEnum type = IncreaseStakingAmountTypeEnum.fromValue(getBigInteger(rootList, 2).intValue());
        BigInteger amount = getBigInteger(rootList, 3);
        return new Tuple3<>(nodeId, type, amount);
    }

    public static Tuple7<String, String, BigInteger, String, String, String, String> decodeInputOfEditStaking(String input) {
        RlpList rootList = (RlpList) RlpDecoder.decode(Hex.decode(input.replace("0x", ""))).getValues().get(0);
        String nodeId = getHex(rootList, 2);
        String benefitAddress = getHex(rootList, 1);
        BigInteger rewardPer = getBigInteger(rootList, 3);
        String externalId = getString(rootList, 4);
        String nodeName =  getString(rootList, 5);
        String website =  getString(rootList, 6);
        String details = getString(rootList, 7);
        return new Tuple7<>(nodeId, benefitAddress, rewardPer, externalId, nodeName, website, details);
    }

    public static Tuple13<String, CreateStakingAmountTypeEnum, String, BigInteger, String, String, String, String, BigInteger, BigInteger, String, String, String> decodeInputOfCreateStaking(String input) {
        RlpList rootList = (RlpList) RlpDecoder.decode(Hex.decode(input.replace("0x", ""))).getValues().get(0);
        String nodeId = getHex(rootList, 3);
        CreateStakingAmountTypeEnum type = CreateStakingAmountTypeEnum.fromValue(getBigInteger(rootList, 1).intValue());
        String benefitAddress = getHex(rootList, 2);
        BigInteger rewardPer = getBigInteger(rootList, 9);
        String externalId = getString(rootList, 4);
        String nodeName =  getString(rootList, 5);
        String website =  getString(rootList, 6);
        String details = getString(rootList, 7);
        BigInteger amount = getBigInteger(rootList, 8);
        BigInteger programVersion = getBigInteger(rootList, 10);
        String programVersionSign = getHex(rootList, 11);
        String blsPubKey = getHex(rootList, 12);
        String blsProof = getHex(rootList, 13);
        return new Tuple13<>(nodeId, type, benefitAddress, rewardPer, externalId, nodeName, website, details, amount, programVersion, programVersionSign, blsPubKey, blsProof);
    }

    private static BigInteger getBigInteger(RlpList rootList, int index){
        if(isNull(rootList, index)){
            return null;
        }
        return ((RlpString) RlpDecoder.decode(((RlpString)rootList.getValues().get(index)).getBytes()).getValues().get(0)).asPositiveBigInteger();
    }

    private static String getString(RlpList rootList, int index){
        if(isNull(rootList, index)){
            return null;
        }
        return new String(Numeric.hexStringToByteArray(((RlpString) RlpDecoder.decode(((RlpString)rootList.getValues().get(index)).getBytes()).getValues().get(0)).asString()),StandardCharsets.UTF_8);
    }

    private static String getHex(RlpList rootList, int index){
        if(isNull(rootList, index)){
            return null;
        }
        return ((RlpString) RlpDecoder.decode(((RlpString)rootList.getValues().get(index)).getBytes()).getValues().get(0)).asString();
    }

    private static boolean isNull(RlpList rootList, int index){
        return ((RlpString)rootList.getValues().get(index)).getBytes().length == 0;
    }
}
