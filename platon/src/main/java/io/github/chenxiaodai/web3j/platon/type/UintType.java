package io.github.chenxiaodai.web3j.platon.type;

import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class UintType implements Type {
    private BigInteger value;

    public UintType(BigInteger value) {
        this.value = value;
    }

    public UintType(Long value) {
        this.value = BigInteger.valueOf(value);
    }

    @Override
    public RlpType getRlpType() {
        return RlpString.create(value);
    }
}
