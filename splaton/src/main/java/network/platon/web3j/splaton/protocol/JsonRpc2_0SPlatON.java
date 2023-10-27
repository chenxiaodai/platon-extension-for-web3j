package network.platon.web3j.splaton.protocol;

import network.platon.web3j.platon.protocol.JsonRpc2_0PlatON;
import network.platon.web3j.splaton.protocol.response.SPlatONGetBlockReceipts;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;

public class JsonRpc2_0SPlatON extends JsonRpc2_0PlatON implements SPlatON {
    public JsonRpc2_0SPlatON(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpc2_0SPlatON(
            Web3jService web3jService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, SPlatONGetBlockReceipts> splatonGetBlockReceipts(BigInteger blockNumber) {
        return new Request<>(
                "platon_getTransactionByBlock",
                Arrays.asList(blockNumber),
                web3jService,
                SPlatONGetBlockReceipts.class);
    }
}
