package io.github.chenxiaodai.web3j.platon.contracts.dto.resp;

import lombok.Data;

import java.math.BigInteger;

@Data
public class RedeemDelegation {

    /**
     * 成功领取的委托金,回到余额
     */
    private BigInteger released;

    /**
     * 成功领取的委托金,回到锁仓账户
     */
    private BigInteger restrictingPlan;
}
