package network.platon.web3j.platon.contracts.enums;

import lombok.Getter;

@Getter
public enum IncreaseStakingAmountTypeEnum {

    FREE_AMOUNT_TYPE(0), RESTRICTING_AMOUNT_TYPE(1);

    int value;

    IncreaseStakingAmountTypeEnum(int val) {
        this.value = val;
    }


    public static IncreaseStakingAmountTypeEnum fromValue(int val) {
        if (val == 0) {
            return FREE_AMOUNT_TYPE;
        } else if (val == 1) {
            return RESTRICTING_AMOUNT_TYPE;
        }
        throw new IllegalArgumentException();
    }
}
