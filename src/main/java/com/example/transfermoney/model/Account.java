package com.example.transfermoney.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="account_name")
	private String accountName;
	
	@Column(name="balance")
	private BigDecimal balance;
	
	@Column(name="currency_code")
	private String currencyCode;
	
	public Account() {}
	
	public Account(String id, String accountName, BigDecimal balance, String currencyCode) {
		this.id = id;
		this.accountName = accountName;
		this.balance = balance;
		this.currencyCode = currencyCode;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getAccountName(){
		return accountName;
	}
	
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	public BigDecimal getBalance(){
		return balance;
	}
	
	public void setBalance(BigDecimal balance){
		this.balance = balance;
	}
	
	public String getCurrencyCode(){
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}
}
