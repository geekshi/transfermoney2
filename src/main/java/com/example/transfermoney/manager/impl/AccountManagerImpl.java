package com.example.transfermoney.manager.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.example.transfermoney.common.ResultStatus;
import com.example.transfermoney.data.AccountRepository;
import com.example.transfermoney.manager.AccountManager;
import com.example.transfermoney.model.Account;
import com.example.transfermoney.model.ResultModel;
import com.example.transfermoney.request.TransferRequest;

@Service
public class AccountManagerImpl implements AccountManager {
	private static final Logger logger = LoggerFactory.getLogger(AccountManagerImpl.class);
	private AccountRepository accountRepository;
	
	@Autowired
	public AccountManagerImpl(AccountRepository accountRepository){
		this.accountRepository = accountRepository;
	}
	
	@Override
	public List<Account> getAccounts(){
		return accountRepository.findAll();
	}
	
	@Override
	public Account getAccountById(String id){
		Optional<Account> res = accountRepository.findById(id);
		Account account = null;
		if(res.isPresent()){
			account = res.get();
		}
		return account;
	}
	
	@Override
	public ResultModel transfer(TransferRequest request){
		try{
			String fromAccountId = request.getFromAccountId();
			String toAccountId = request.getToAccountId();
			String currencyCode = request.getCurrencyCode();
			BigDecimal amount = new BigDecimal(request.getAmount());
			if(fromAccountId==null || toAccountId==null || currencyCode==null){
				return ResultModel.error(ResultStatus.BAD_FORMAT);
			}
			if(fromAccountId.equals(toAccountId)){
				return ResultModel.error(ResultStatus.SAME_ACCOUNT);
			}
			Account source = getAccountById(fromAccountId);
			Account destination = getAccountById(toAccountId);
			if(source==null || destination==null){
				return ResultModel.error(ResultStatus.NOT_FOUND);
			}
			if (!source.getCurrencyCode().equals(currencyCode) ||
				!destination.getCurrencyCode().equals(currencyCode)){
				return ResultModel.error(ResultStatus.CONVERSATION_FORBIDDEN);
			}
			if(source.getBalance().compareTo(amount) < 0){
				return ResultModel.error(ResultStatus.NO_MONEY);
			}
			source.setBalance(source.getBalance().subtract(amount));
			destination.setBalance(destination.getBalance().add(amount));
			accountRepository.save(source);
			accountRepository.save(destination);
			return ResultModel.ok();
		}catch(NumberFormatException e){
			return ResultModel.error(ResultStatus.BAD_FORMAT);
		}catch(JSONException e){
			return ResultModel.error(ResultStatus.JSON_ERROR);
		}catch(Exception e){
			logger.error("Unknown error: ", e);
			return ResultModel.error(ResultStatus.TRANSFER_FAILED);
		}
	}
}
