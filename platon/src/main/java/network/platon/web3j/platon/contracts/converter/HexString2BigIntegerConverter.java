package network.platon.web3j.platon.contracts.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class HexString2BigIntegerConverter extends StdConverter<String, BigInteger> {
    @Override
    public BigInteger convert(String value) {
        return Numeric.toBigInt(value);
    }
}
