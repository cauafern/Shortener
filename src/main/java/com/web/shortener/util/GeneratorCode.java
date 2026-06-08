package com.web.shortener.util;

import java.security.SecureRandom;

public class GeneratorCode {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateCode(int size){
    SecureRandom random = new SecureRandom();
    StringBuilder builder = new StringBuilder();
    for(int i=0;i<size;i++){
        builder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return builder.toString();
    }
}
