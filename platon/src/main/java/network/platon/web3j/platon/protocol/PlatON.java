package network.platon.web3j.platon.protocol;

import network.platon.web3j.platon.PlatONConstant;
import network.platon.web3j.platon.protocol.response.AdminBlsProof;
import network.platon.web3j.platon.protocol.response.AdminProgramVersion;
import network.platon.web3j.platon.protocol.response.DebugEconomicConfig;
import network.platon.web3j.platon.protocol.response.PlatonEvidences;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.Request;
import org.web3j.utils.Async;

public interface PlatON extends Admin {
    static PlatON build(Web3jService web3jService) {
        return new JsonRpc2_0PlatON(web3jService, PlatONConstant.SLEEP_DURATION, Async.defaultExecutorService());
    }
    Request<?, AdminProgramVersion> getProgramVersion();
    Request<?, AdminBlsProof> getBlsProof();
    Request<?, PlatonEvidences> platonEvidences();
    Request<?, DebugEconomicConfig> getEconomicConfig();
}
