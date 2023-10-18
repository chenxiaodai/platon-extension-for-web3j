package io.github.chenxiaodai.web3j.platon.contracts.dto.resp;

import lombok.Data;

import java.math.BigInteger;
import java.util.Optional;

@Data
public class UnDelegation {

    /**
     * 委托的收益
     */
    private BigInteger delegateIncome;

    /**
     * 撤销的委托金退回用户余额 （节点1.3.0后版本存在）
     */
    private Optional<BigInteger> released = Optional.empty();

    /**
     * 撤销的委托金退回用户锁仓账户（节点1.3.0后版本存在）
     */
    private Optional<BigInteger> restrictingPlan = Optional.empty();

    /**
     * 撤销的委托金转到锁定期,来自余额（节点1.3.0后版本存在）
     */
    private Optional<BigInteger> lockReleased = Optional.empty();

    /**
     * 撤销的委托金转到锁定期,来自锁仓账户（节点1.3.0后版本存在）
     */
    private Optional<BigInteger> lockRestrictingPlan = Optional.empty();
}
