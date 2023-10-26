package network.platon.web3j.platon.contracts.type;

import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;

import java.util.ArrayList;
import java.util.List;

public class ListType<T extends Type> implements Type<List<T>> {

    private List<T> value;

    public ListType(List<T> value) {
        this.value = value;
    }

    @Override
    public RlpType getRlpType() {
        List<RlpType> rlpListList = new ArrayList<>();
        for (Type t : value) {
            rlpListList.add(t.getRlpType());
        }
        return new RlpList(rlpListList);
    }

    @Override
    public List<T> getValue() {
        return value;
    }
}
