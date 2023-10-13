package io.github.chenxiaodai.web3j.platon.enums;

import lombok.Getter;

@Getter
public enum DelegateAmountTypeEnum {

    BALANCE(0),
    RESTRICTING(1),
    DELEGATE_LOCK(3);
    private final int value;
    DelegateAmountTypeEnum(int val) {
        this.value = val;
    }
}
