package network.platon.web3j.splaton.protocol;

import network.platon.web3j.platon.PlatONConstant;
import network.platon.web3j.platon.protocol.PlatON;
import network.platon.web3j.splaton.protocol.response.SPlatONGetBlockReceipts;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.utils.Async;

import java.math.BigInteger;

public interface SPlatON extends PlatON {
    static SPlatON build(Web3jService web3jService) {
        return new JsonRpc2_0SPlatON(web3jService, PlatONConstant.SLEEP_DURATION, Async.defaultExecutorService());
    }

    Request<?, SPlatONGetBlockReceipts> splatonGetBlockReceipts(BigInteger blockNumber);
}
