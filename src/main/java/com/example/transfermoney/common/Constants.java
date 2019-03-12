package com.example.transfermoney.common;

public class Constants {
	public static final String INIT = "/init";
	public static final String ACCOUNTS = "/accounts";
	public static final String TRANSACTION = "/transaction";
	public static final String HOST = "http://localhost:8080";
	
	private Constants(){
		throw new IllegalStateException("Utility class");
	}
}
