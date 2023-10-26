package network.platon.web3j.platon.contracts.enums;

import lombok.Getter;

@Getter
public enum DuplicateSignTypeEnum {

    PREPARE_BLOCK(1), PREPARE_VOTE(2),VIEW_CHANGE(3);

    final int value;

    DuplicateSignTypeEnum(int val) {
        this.value = val;
    }
}
