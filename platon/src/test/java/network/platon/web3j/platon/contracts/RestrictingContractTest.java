package network.platon.web3j.platon.contracts;

import network.platon.web3j.platon.contracts.dto.CallResponse;
import network.platon.web3j.platon.contracts.dto.TransactionResponse;
import network.platon.web3j.platon.contracts.dto.req.RestrictingPlan;
import network.platon.web3j.platon.contracts.dto.resp.RestrictingItem;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestrictingContractTest extends BaseContractTester {


    @Test
    public void createRestrictingPlan() throws Exception {
        prepareTransaction();

        Credentials stakingCredentials = Credentials.create("0x690a32ceb7eab4131f7be318c1672d3b9b2dadeacba20b99432a7847c1e926e0");
        RestrictingContract restrictingContract = RestrictingContract.load(web3j, getVerifiedTransactionManager(stakingCredentials));
        Credentials deleteCredentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");
        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(1000), new BigInteger("1000000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(2000), new BigInteger("1000000000000000000000")));
        TransactionResponse response = restrictingContract.createRestrictingPlan(deleteCredentials.getAddress(), restrictingPlans).send();
        assertTrue(response.isStatusOk());
    }


    @Test
    public void getRestrictingInfo() throws Exception {
        prepareCall("0x7b22436f6465223a302c22526574223a7b2262616c616e6365223a223078366336623933356238626264343030303030222c2264656274223a22307830222c22706c616e73223a5b7b22626c6f636b4e756d626572223a343730383332302c22616d6f756e74223a223078333633356339616463356465613030303030227d2c7b22626c6f636b4e756d626572223a343836383332302c22616d6f756e74223a223078333633356339616463356465613030303030227d5d2c22506c65646765223a22307830227d7d");

        Credentials deleteCredentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");
        RestrictingContract restrictingContract = RestrictingContract.load(web3j);
        CallResponse<RestrictingItem> response = restrictingContract.getRestrictingInfo(deleteCredentials.getAddress()).send();
        assertTrue(response.isStatusOk());
    }
}
