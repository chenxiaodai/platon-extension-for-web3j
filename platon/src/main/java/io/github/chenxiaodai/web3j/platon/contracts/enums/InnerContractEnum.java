package io.github.chenxiaodai.web3j.platon.contracts.enums;

import io.github.chenxiaodai.web3j.platon.utils.Bech32Utils;
import lombok.Getter;

@Getter
public enum InnerContractEnum {
    RESTRICTING_CONTRACT("0x1000000000000000000000000000000000000001"),
    STAKING_CONTRACT("0x1000000000000000000000000000000000000002"),
    SLASHING_CONTRACT("0x1000000000000000000000000000000000000004"),
    GOV_CONTRACT("0x1000000000000000000000000000000000000005"),
    DELEGATE_REWARD_CONTRACT("0x1000000000000000000000000000000000000006");

    private final String address;
    InnerContractEnum(String address) {
        this.address = address;
    }

    public String getLatAddress(){
        return Bech32Utils.encodeWithLat(address);
    }
}
