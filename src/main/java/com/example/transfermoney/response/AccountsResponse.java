package com.example.transfermoney.response;

import java.util.List;


public class AccountsResponse {
	int totalAccounts;
	List<AccountBrief> accounts;
	
	public void setTotalAccounts(int totalAccounts){
		this.totalAccounts = totalAccounts;
	}
	
	public int getTotalAccounts(){
		return totalAccounts;
	}
	
	public void setAccounts(List<AccountBrief> accounts){
		this.accounts = accounts;
	}
	
	public List<AccountBrief> getAccounts(){
		return accounts;
	}
}
