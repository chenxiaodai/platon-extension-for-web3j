package io.github.chenxiaodai.web3j.platon.contracts.type;

import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

public class StringType implements Type<String> {
    private String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public RlpType getRlpType() {
        return RlpString.create(value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
