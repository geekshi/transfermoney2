package com.example.transfermoney.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.transfermoney.common.Constants;
import com.example.transfermoney.common.ResultStatus;
import com.example.transfermoney.controller.util.ControllerUtil;
import com.example.transfermoney.manager.AccountManager;
import com.example.transfermoney.model.Account;
import com.example.transfermoney.model.ResultModel;
import com.example.transfermoney.request.TransferRequest;
import com.example.transfermoney.response.AccountsResponse;

@RestController
@Api(value = "", tags = "RESTful API")
public class RestfulController {
	
	@Autowired
	private AccountManager manager;

	@GetMapping(Constants.ACCOUNTS)
	@ApiOperation(value = "show all accounts", notes="show all accounts' urls")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = AccountsResponse.class)})
	public ResponseEntity<Object> getAccounts(){
		List<Account> accounts = manager.getAccounts();
		return new ResponseEntity<>(ControllerUtil.wrapAccounts(accounts), HttpStatus.OK);
	}
	
	@GetMapping(Constants.ACCOUNTS + "/{id}")
	@ApiOperation(value = "show one account", notes="show one account's balance")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Account.class)})
	public ResponseEntity<Object> showAccount(@PathVariable @ApiParam(value = "account id", required = true) String id){
		Account a = manager.getAccountById(id);
		if(a == null){
			return new ResponseEntity<>(ResultModel.error(ResultStatus.NOT_FOUND), HttpStatus.OK);
		}
		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	
	@PostMapping(Constants.TRANSACTION)
	@ApiOperation(value = "transfer money", notes="make a money transaction")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = ResultModel.class),
							@ApiResponse(code = 400, message = "Bad request", response = ResultModel.class)})
	public ResponseEntity<Object> transfer(@RequestBody TransferRequest request){
		return ControllerUtil.wrapResponse(manager.transfer(request), request);
	}
}
