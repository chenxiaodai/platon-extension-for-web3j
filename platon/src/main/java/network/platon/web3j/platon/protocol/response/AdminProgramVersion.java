package network.platon.web3j.platon.protocol.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.web3j.protocol.core.Response;

import java.math.BigInteger;

/**
 * platon_evidences.
 */
public class AdminProgramVersion extends Response<AdminProgramVersion.ProgramVersion> {

    @Data
    public static class ProgramVersion {

        /**
         * 代码版本
         */
        @JsonProperty("Version")
        private BigInteger version;
        /**
         * 代码版本签名
         */
        @JsonProperty("Sign")
        private String sign;
    }

}
