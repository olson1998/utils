package com.olson1998.utils;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Base64Utils {

    public static String encode(String phrase){
        var encBytes = Base64.getEncoder().encode(phrase.getBytes(UTF_8));
        return new String(encBytes);
    }

    public static String decode(String phrase){
        var decBytes = Base64.getDecoder().decode(phrase.getBytes(UTF_8));
        return new String(decBytes);
    }

    private Base64Utils() {
    }
}
