package com.scrumsys.userservice.util;


import java.security.SecureRandom;

public final class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "@#$%&*!";
    private static final String ALL = LOWER + UPPER + DIGITS + SPECIAL;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordGenerator() {}

    public static String generate() {
        int length = 10; // recommended minimum
        StringBuilder password = new StringBuilder(length);

        // Ensure strong password
        password.append(randomChar(UPPER));
        password.append(randomChar(LOWER));
        password.append(randomChar(DIGITS));
        password.append(randomChar(SPECIAL));

        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL));
        }

        return shuffle(password.toString());
    }

    private static char randomChar(String chars) {
        return chars.charAt(RANDOM.nextInt(chars.length()));
    }

    private static String shuffle(String input) {
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}

