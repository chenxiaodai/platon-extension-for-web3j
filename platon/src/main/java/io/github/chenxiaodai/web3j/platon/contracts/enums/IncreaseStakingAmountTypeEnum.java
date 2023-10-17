package io.github.chenxiaodai.web3j.platon.contracts.enums;

import lombok.Getter;

@Getter
public enum IncreaseStakingAmountTypeEnum {

    FREE_AMOUNT_TYPE(0), RESTRICTING_AMOUNT_TYPE(1);

    int value;

    IncreaseStakingAmountTypeEnum(int val) {
        this.value = val;
    }
}
