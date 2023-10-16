package io.github.chenxiaodai.web3j.platon.type;

import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

public class HexStringType implements Type {
    private String value;

    public HexStringType(String value) {
        this.value = value;
    }

    @Override
    public RlpType getRlpType() {
        return RlpString.create(Numeric.hexStringToByteArray(value));
    }
}
