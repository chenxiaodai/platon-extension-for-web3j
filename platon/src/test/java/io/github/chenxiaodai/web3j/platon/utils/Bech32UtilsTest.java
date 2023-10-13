package io.github.chenxiaodai.web3j.platon.utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bech32UtilsTest {
    @Test
    public void decode() {
        assertEquals(
                "0xadb991bf18a0d930b538d6c24773f6b90dba4109",
                Bech32Utils.decode("lat14kuer0cc5rvnpdfc6mpywulkhyxm5sgfema80l")
        );
    }

    @Test
    public void encode() {
        assertEquals(
                "atp14kuer0cc5rvnpdfc6mpywulkhyxm5sgfqdtlss",
                Bech32Utils.encode("atp","0xadb991bf18a0d930b538d6c24773f6b90dba4109")
        );
    }

    @Test
    public void encodeWithLat() {
        assertEquals(
                "lat14kuer0cc5rvnpdfc6mpywulkhyxm5sgfema80l",
                Bech32Utils.encodeWithLat("0xadb991bf18a0d930b538d6c24773f6b90dba4109")
        );
    }
}