package network.platon.web3j.platon.contracts.enums;

import lombok.Getter;

@Getter
public enum DelegateAmountTypeEnum {

    BALANCE(0),
    RESTRICTING(1),
    DELEGATE_LOCK(3);
    int value;
    DelegateAmountTypeEnum(int val) {
        this.value = val;
    }
}
