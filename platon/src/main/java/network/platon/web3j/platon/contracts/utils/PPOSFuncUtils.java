package network.platon.web3j.platon.contracts.utils;

import network.platon.web3j.platon.contracts.common.Function;
import network.platon.web3j.platon.contracts.type.Type;
import network.platon.web3j.platon.contracts.type.UintType;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.util.ArrayList;
import java.util.List;

public class PPOSFuncUtils {

    public static String encode(Function function) {
        List<RlpType> result = new ArrayList<>();
        result.add(RlpString.create(RlpEncoder.encode(new UintType(function.getType()).getRlpType())));
        for (Type param: function.getInputParameters()) {
            result.add(RlpString.create(RlpEncoder.encode(param.getRlpType())));
        }
        return Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
    }
}
