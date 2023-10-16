package io.github.chenxiaodai.web3j.platon.contracts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CallResponse<T> extends BaseResponse {

    @JsonProperty("Ret")
    private T data;
}
