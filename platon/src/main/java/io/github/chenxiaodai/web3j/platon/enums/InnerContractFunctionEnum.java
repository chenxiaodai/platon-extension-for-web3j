package io.github.chenxiaodai.web3j.platon.enums;

import lombok.Getter;

@Getter
public enum InnerContractFunctionEnum {
    STAKING_CONTRACT_DELEGATE(1004);

    private final int type;
    InnerContractFunctionEnum(int type) {
        this.type = type;
    }
}
