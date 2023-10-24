package io.github.chenxiaodai.web3j.splaton.protocol;

import io.github.chenxiaodai.web3j.platon.PlatONConstant;
import io.github.chenxiaodai.web3j.platon.protocol.PlatON;
import org.web3j.protocol.Web3jService;
import org.web3j.utils.Async;

public interface SPlatON extends PlatON {
    static SPlatON build(Web3jService web3jService) {
        return new JsonRpc2_0SPlatON(web3jService, PlatONConstant.SLEEP_DURATION, Async.defaultExecutorService());
    }
}
