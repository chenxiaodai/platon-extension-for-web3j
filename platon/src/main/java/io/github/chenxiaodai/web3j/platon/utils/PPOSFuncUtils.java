package io.github.chenxiaodai.web3j.platon.utils;

import io.github.chenxiaodai.web3j.platon.type.Type;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.util.ArrayList;
import java.util.List;

public class PPOSFuncUtils {

    public static String encode(List<Type> params) {
        List<RlpType> result = new ArrayList<>();
        for (Type param: params) {
            result.add(RlpString.create(RlpEncoder.encode(param.getRlpType())));
        }
        return Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
    }
}
