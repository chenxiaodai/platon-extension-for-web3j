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

    public static DelegateAmountTypeEnum fromValue(int val) {
        if (val == 0) {
            return BALANCE;
        } else if (val == 1) {
            return RESTRICTING;
        } else if (val == 2) {
            return DELEGATE_LOCK;
        }
        throw new IllegalArgumentException();
    }
}
