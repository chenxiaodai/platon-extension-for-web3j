package io.github.chenxiaodai.web3j.platon.contracts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.chenxiaodai.web3j.platon.contracts.common.ErrorCode;
import lombok.*;

@Data
public class BaseResponse {

    @JsonProperty("Code")
    private int code;
    private String errMsg;
    private boolean statusOk;

    public boolean isStatusOk() {
        return code == ErrorCode.SUCCESS;
    }

    public String getErrMsg() {
        return ErrorCode.getErrorMsg(code);
    }
}
