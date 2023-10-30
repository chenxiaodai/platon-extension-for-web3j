package network.platon.web3j.splaton.contracts.dto.resp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import network.platon.web3j.platon.contracts.converter.Bech32Address2HexAddressConverter;
import network.platon.web3j.platon.contracts.converter.HexString2BigIntegerConverter;

import java.math.BigInteger;
import java.util.List;


@Data
public class Balance {

    @JsonDeserialize(converter = Bech32Address2HexAddressConverter.class)
    private String account;

    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger freeBalance;

    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger lockBalance;

    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger pledgeBalance;

    /**
     * 委托锁定待提取中余额部分
     */
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger dlFreeBalance;

    /**
     * 委托锁定待提取中锁仓部分
     */
    @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
    private BigInteger dlRestrictingBalance;

    /**
     * 委托锁定中锁定的列表
     */
    private List<DlLock> dlLocks;


    public static class DlLock {

        /**
         * 锁仓金额
         */
        @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
        private BigInteger lockBalance;

        /**
         * 自由金金额
         */
        @JsonDeserialize(converter = HexString2BigIntegerConverter.class)
        private BigInteger freeBalance;

        /**
         * 解锁结算周期
         */
        private BigInteger epoch;
    }
}
