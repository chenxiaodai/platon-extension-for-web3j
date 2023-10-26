package network.platon.web3j.platon.contracts.type;

import org.web3j.rlp.RlpType;

public interface Type<T> {

    RlpType getRlpType();

    T getValue();
}
