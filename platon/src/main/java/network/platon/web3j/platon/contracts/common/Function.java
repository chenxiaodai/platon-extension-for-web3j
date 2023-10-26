package network.platon.web3j.platon.contracts.common;

import network.platon.web3j.platon.contracts.type.Type;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Function {

    private int type;

    private List<Type> inputParameters;

    public Function(int type) {
        this.type = type;
        this.inputParameters = new ArrayList<>();
    }

    public Function(int type, List<Type> inputParameters) {
        this.type = type;
        this.inputParameters = inputParameters;
    }
}
