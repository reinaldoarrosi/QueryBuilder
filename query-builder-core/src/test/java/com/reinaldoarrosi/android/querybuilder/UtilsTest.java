package com.reinaldoarrosi.android.querybuilder;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void isNullOrWhiteSpace() throws Exception {
        assertTrue(Utils.isNullOrWhiteSpace(null));

        assertTrue(Utils.isNullOrWhiteSpace(""));

        assertTrue(Utils.isNullOrWhiteSpace(" "));

        assertFalse(Utils.isNullOrWhiteSpace("a"));
    }

    @Test
    public void isNullOrEmpty() throws Exception {
        assertTrue(Utils.isNullOrEmpty(null));

        assertTrue(Utils.isNullOrEmpty(""));

        assertFalse(Utils.isNullOrEmpty(" "));

        assertFalse(Utils.isNullOrEmpty("a"));
    }

}