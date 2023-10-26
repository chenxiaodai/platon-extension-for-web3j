package network.platon.web3j.platon.contracts.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GovernParam {

    @JsonProperty("ParamItem")
    private ParamItem paramItem;

    @JsonProperty("ParamValue")
    private ParamValue paramValue;


    @Data
    public static class ParamItem {
        /**
         * 参数模块
         */
        @JsonProperty("Module")
        private String module;
        /**
         * 参数名称
         */
        @JsonProperty("Name")
        private String name;
        /**
         * 参数说明
         */
        @JsonProperty("Desc")
        private String desc;

    }

    @Data
    public class ParamValue {

        /**
         * 旧参数值
         */
        @JsonProperty("StaleValue")
        private String staleValue;
        /**
         * 参数值
         */
        @JsonProperty("Value")
        private String value;
        /**
         * 块高。(>=ActiveBLock，将取Value;否则取StaleValue)
         */
        @JsonProperty("ActiveBlock")
        private String activeBlock;
    }
}
