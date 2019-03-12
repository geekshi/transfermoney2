package com.example.transfermoney.controller.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.example.transfermoney.common.Constants;
import com.example.transfermoney.common.ResultStatus;
import com.example.transfermoney.model.Account;
import com.example.transfermoney.model.ResultModel;
import com.example.transfermoney.request.TransferRequest;
import com.example.transfermoney.response.AccountBrief;
import com.example.transfermoney.response.AccountsResponse;

public class ControllerUtil {
	private static final Logger logger = LoggerFactory.getLogger(ControllerUtil.class);
	
	private ControllerUtil(){
		throw new IllegalStateException("Utility class");
	}

	public static Object wrapAccounts(List<Account> accounts){
		AccountsResponse response = new AccountsResponse();
		response.setTotalAccounts(accounts.size());
		List<AccountBrief> array = new ArrayList<>();
		for(Account account : accounts){
			AccountBrief brief = new AccountBrief(account.getId(), 
					account.getAccountName(), Constants.ACCOUNTS + "/" + account.getId());
			array.add(brief);
		}
		response.setAccounts(array);
		return response;
	}
	
	public static ResponseEntity<Object> wrapResponse(ResultModel response, TransferRequest request){
		try{
			String fromAccountId = request.getFromAccountId();
			String toAccountId = request.getToAccountId();
			if(response.getCode() != ResultStatus.NOT_FOUND.getCode()){
				if(response.getCode() != ResultStatus.SAME_ACCOUNT.getCode()){
					JSONArray arr = new JSONArray();
					arr.add(JSON.parse(callShowAccount(fromAccountId)));
					arr.add(JSON.parse(callShowAccount(toAccountId)));
					response.setContent(arr);
				}else{
					response.setContent(JSON.parse(callShowAccount(fromAccountId)));
				}
			}
		}catch(JSONException e){
			logger.error("Json error: ", e);
		}catch(Exception e){
			logger.error("Unknown error: ", e);
		}
		return new ResponseEntity<>(response, getStatus(response));
	}
	
	public static String callShowAccount(String id){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = null;
		String url = Constants.HOST + Constants.ACCOUNTS + "/" + id;
		httpGet = new HttpGet(url);
		CloseableHttpResponse resp = null;
		String jString = "";
		try {
			resp = client.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			jString = EntityUtils.toString(entity);
		} catch (Exception e) {
			logger.error("Unknown error: ", e);
		} finally{
			try {
				if(resp != null){
					resp.close();
				}
			} catch (Exception e) {
				logger.error("Close resp error: ", e);
			}
			try{
				client.close();
			} catch (Exception e) {
				logger.error("Close client error: ", e);
			}
		}
		return jString;
	}
	
	private static HttpStatus getStatus(ResultModel response){
		return response.getCode() == ResultStatus.SUCCESS.getCode() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
	}
}
