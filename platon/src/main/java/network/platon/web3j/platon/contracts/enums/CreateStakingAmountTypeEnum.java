package network.platon.web3j.platon.contracts.enums;

import lombok.Getter;

@Getter
public enum CreateStakingAmountTypeEnum {

    FREE_AMOUNT_TYPE(0), RESTRICTING_AMOUNT_TYPE(1), AUTO_AMOUNT_TYPE(2);

    int value;

    CreateStakingAmountTypeEnum(int val) {
        this.value = val;
    }

    public static CreateStakingAmountTypeEnum fromValue(int val) {
        if (val == 0) {
            return FREE_AMOUNT_TYPE;
        } else if (val == 1) {
            return RESTRICTING_AMOUNT_TYPE;
        } else if (val == 2) {
            return AUTO_AMOUNT_TYPE;
        }
        throw new IllegalArgumentException();
    }
}
