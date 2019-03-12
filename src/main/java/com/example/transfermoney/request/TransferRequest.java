package com.example.transfermoney.request;


public class TransferRequest {
	String fromAccountId;
	String toAccountId;
	String currencyCode;
	String amount;
	
	public TransferRequest(){}
	
	public TransferRequest(String fromAccountId, String toAccountId, String currencyCode, String amount){
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.currencyCode = currencyCode;
		this.amount = amount;
	}
	
	public String getFromAccountId(){
		return fromAccountId;
	}
	
	public String getToAccountId(){
		return toAccountId;
	}
	
	public String getCurrencyCode(){
		return currencyCode;
	}
	
	public String getAmount(){
		return amount;
	}
}
