package com.example.transfermoney.response;


public class AccountBrief {
	String id;
	String name;
	String herf;
	
	public AccountBrief(){}
	
	public AccountBrief(String id, String name, String herf){
		this.id = id;
		this.herf = herf;
		this.name = name;
	}
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getHerf(){
		return herf;
	}
}
