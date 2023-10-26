package network.platon.web3j.platon.protocol;

import network.platon.web3j.platon.protocol.response.AdminProgramVersion;
import network.platon.web3j.platon.protocol.response.AdminBlsProof;
import network.platon.web3j.platon.protocol.response.PlatonEvidences;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.JsonRpc2_0Admin;
import org.web3j.protocol.core.Request;

import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;

public class JsonRpc2_0PlatON extends JsonRpc2_0Admin implements PlatON {
    public JsonRpc2_0PlatON(Web3jService web3jService) {
        super(web3jService);
    }

    public JsonRpc2_0PlatON(
            Web3jService web3jService,
            long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, AdminProgramVersion> getProgramVersion() {
        return new Request<>(
                "admin_getProgramVersion",
                Collections.<String>emptyList(),
                web3jService,
                AdminProgramVersion.class);
    }

    @Override
    public Request<?, AdminBlsProof> getBlsProof() {
        return new Request<>(
                "admin_getSchnorrNIZKProve",
                Collections.<String>emptyList(),
                web3jService,
                AdminBlsProof.class);
    }

    @Override
    public Request<?, PlatonEvidences> platonEvidences() {
        return new Request<>(
                "platon_evidences",
                Collections.<String>emptyList(),
                web3jService,
                PlatonEvidences.class);
    }
}
