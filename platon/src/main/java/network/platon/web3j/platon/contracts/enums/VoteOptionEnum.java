package network.platon.web3j.platon.contracts.enums;

import lombok.Getter;

@Getter
public enum VoteOptionEnum {

    YEAS(1), NAYS(2), ABSTENTIONS(3);

    int value;

    VoteOptionEnum(int val) {
        this.value = val;
    }
}
