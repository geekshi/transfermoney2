package com.example.transfermoney.manager;

import java.util.List;

import com.example.transfermoney.model.Account;
import com.example.transfermoney.model.ResultModel;
import com.example.transfermoney.request.TransferRequest;


public interface AccountManager {
	List<Account> getAccounts();
	Account getAccountById(String id);
	ResultModel transfer(TransferRequest request);
}
