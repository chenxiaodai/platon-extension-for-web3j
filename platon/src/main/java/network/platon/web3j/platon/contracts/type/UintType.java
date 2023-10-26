package network.platon.web3j.platon.contracts.type;

import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.math.BigInteger;

public class UintType implements Type<BigInteger> {
    private BigInteger value;

    public UintType(BigInteger value) {
        this.value = value;
    }

    public UintType(Long value) {
        this.value = BigInteger.valueOf(value);
    }

    public UintType(Integer value) {
        this.value = BigInteger.valueOf(value);
    }

    @Override
    public RlpType getRlpType() {
        return RlpString.create(value);
    }

    @Override
    public BigInteger getValue() {
        return value;
    }
}
