package com.boolment.jobapp.util;

public class SecurityConstant {

	public static final long  EXPIRATION_DATE = 432_000_000; // i have set for 5 days in milliseconds
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token can not be verified";
	
	public static final String GET_BPSERVICES = "BPSERVICES PVT";
	public static final String GET_JOB_ADMINISTRATION = "Job Portal Bpservices";
	public static final String AUTHORITIES = "authorities";
	public static final String FORBIDDEN_MESSAGE = "You need to loggin to access this page";
	public static final String ACCESS_DENIED_MESSAGE = "You don't have premssion to access this page";
	public static final String OPTIONS_HTTP_METHOD  = "OPTIONS";
//	public static final String[] PUBLIC_URLS = {"/user/login","/user/register", "/user/resetpassword"};
	
//	for testing do not block any url
	public static final String[] PUBLIC_URLS = {"**"};
}
