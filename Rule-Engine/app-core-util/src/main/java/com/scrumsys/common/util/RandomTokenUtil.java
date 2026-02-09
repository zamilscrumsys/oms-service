package com.scrumsys.common.util;

import java.util.UUID;

public class RandomTokenUtil {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
    public static String generateOTP(int length) {
        return String.format("%0" + length + "d",
                (int)(Math.random() * Math.pow(10, length)));
    }
}

