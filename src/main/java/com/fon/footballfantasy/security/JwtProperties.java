package com.fon.footballfantasy.security;

public class JwtProperties {
	
	public static final String SECRET = "SECURESECURESECURESECURESECURESECURESECURE";
	public static final long EXPIRATION_TIME = 10; // days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

}
