package com.application.tools;

public class RandomStringGenerator {

    public static String generate(int size) {
        final String ALPHA_NUMERIC_STRING = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ)0!1@2#3$4%5^6&7*8(9";
        StringBuilder builder = new StringBuilder();
        while (size-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
