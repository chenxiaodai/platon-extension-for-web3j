package io.github.chenxiaodai.web3j.splaton.protocol;

import io.github.chenxiaodai.web3j.platon.protocol.JsonRpc2_0PlatON;
import org.web3j.protocol.Web3jService;

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
}
