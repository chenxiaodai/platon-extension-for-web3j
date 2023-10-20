package io.github.chenxiaodai.web3j.platon.contracts.dto.req;

import io.github.chenxiaodai.web3j.platon.contracts.type.Type;
import lombok.Data;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.math.BigInteger;

@Data
public class RestrictingPlan implements Type<RestrictingPlan> {

    /**
     * 表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。如果 account 是激励池地址，
     * 那么 period 值是 120（即，30*4） 的倍数。另外，period * 每周期的区块数至少要大于最高不可逆区块高度
     */
    private final BigInteger epoch;

    /**
     * 表示目标区块上待释放的金额
     */
    private final BigInteger amount;

    @Override
    public RlpType getRlpType() {
        return new RlpList(RlpString.create(epoch), RlpString.create(amount));
    }

    @Override
    public RestrictingPlan getValue() {
        return this;
    }
}
