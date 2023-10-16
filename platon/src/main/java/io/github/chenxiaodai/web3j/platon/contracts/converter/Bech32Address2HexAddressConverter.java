package io.github.chenxiaodai.web3j.platon.contracts.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import io.github.chenxiaodai.web3j.platon.utils.Bech32Utils;

public class Bech32Address2HexAddressConverter extends StdConverter<String, String> {
    @Override
    public String convert(String value) {
        return Bech32Utils.decode(value);
    }
}
