package com.example.demo.utils;

import java.util.Random;

public class Generator {

    private final Integer LEN = 64;

    public String generate(){
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(LEN);
        for (int i = 0; i < LEN; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
