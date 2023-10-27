package network.platon.web3j.splaton.protocol.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import network.platon.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import org.web3j.protocol.core.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SPlatONGetBlockReceipts extends Response<List<SPlatONGetBlockReceipts.SPlatONTransactionReceipt>> {

    public Optional<List<SPlatONTransactionReceipt>> getBlockReceipts() {
        return Optional.ofNullable(getResult());
    }

    @Data
    public static class SPlatONTransactionReceipt {
        private String transactionHash;
        private String transactionIndex;
        private String gasUsed;
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String contractAddress;
        private String root;
        private String status;
        private List<Log> logs;
        private List<ContractInfo> contractCreated;
        private List<ContractInfo> contractSuicided = new ArrayList<>();
        private List<EmbedTransfer> embedTransfer;

    }

    @Data
    public static class ContractInfo {
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String address;
    }

    @Data
    public static class EmbedTransfer {
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String from;
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String to;
        private BigDecimal value;
    }

    @Data
    public static class Log {
        @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
        private String address;
        private List<String> topics;
        private String data;
        private boolean removed;
        private String logIndex;
    }
}
