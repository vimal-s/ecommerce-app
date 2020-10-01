package com.example.demo.security;

public class Constant {

    public static final long VALIDITY_DURATION = 604_800_000;  // 7 days in milliseconds
    public static final String SECRET = "ThisIsASecretKey";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";   // space suffixed is required
}