package io.github.chenxiaodai.web3j.platon.utils;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class VersionStrUtilsTest {
    @Test
    public void versionInterToStr() {
        assertEquals(
                "1.5.0",
                VersionStrUtils.versionInterToStr(BigInteger.valueOf(66816L))
        );
    }

    @Test
    public void versionStrToInteger() {
        assertEquals(
                BigInteger.valueOf(66816L),
                VersionStrUtils.versionStrToInteger("1.5.0")
        );
    }
}