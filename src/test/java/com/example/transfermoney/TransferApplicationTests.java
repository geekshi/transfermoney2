package com.example.transfermoney;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.alibaba.fastjson.JSON;
import com.example.transfermoney.common.Constants;
import com.example.transfermoney.manager.AccountManager;
import com.example.transfermoney.model.Account;
import com.example.transfermoney.request.TransferRequest;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class TransferApplicationTests {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AccountManager manager;
	 
	@Test
	public void testInit() throws Exception {
		mvc.perform(get(Constants.ACCOUNTS).accept(MediaType.APPLICATION_JSON))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accounts", hasSize(3)));
	}
	
	@Test
	public void testAccountDetail() throws Exception {
		mvc.perform(get(Constants.ACCOUNTS + "/1").accept(MediaType.APPLICATION_JSON))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accountName", is("USD Account")));
	}
	
	@Test
	public void testAccountNotFound() throws Exception {
		mvc.perform(get(Constants.ACCOUNTS + "/4").accept(MediaType.APPLICATION_JSON))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath(MESSAGE, is("Account not found")));
	}

	@Test
	public void testTransferSuccess() throws Exception {		
		TransferRequest request = new TransferRequest("2", "3", "HKD", "50.25");
		mvc.perform(post(Constants.TRANSACTION).accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(JSON.toJSONString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath(MESSAGE, is("Success")));
	}

	@Test
	public void testTransferFailedTheSameAccounts() throws Exception {
		TransferRequest request = new TransferRequest("2", "2", "HKD", "250");
		mvc.perform(post(Constants.TRANSACTION).accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(JSON.toJSONString(request)))
			.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
			.andExpect(jsonPath(MESSAGE, is("The same accounts")));
	}
	
	@Test
	public void testTransferFailedConversationForbidden() throws Exception {
		TransferRequest request = new TransferRequest("1", "2", "USD", "500");
		mvc.perform(post(Constants.TRANSACTION).accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(JSON.toJSONString(request)))
			.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
			.andExpect(jsonPath(MESSAGE, is("Conversation not allowed")));
	}
	
	@Test
	public void testTransferFailedInsufficientFunds() throws Exception {
		TransferRequest request = new TransferRequest("2", "3", "HKD", "1001");
		mvc.perform(post(Constants.TRANSACTION).accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(JSON.toJSONString(request)))
			.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
			.andExpect(jsonPath(MESSAGE, is("Insufficient funds")));
	}
	
	@Test
	public void testTransferFailedInputError() throws Exception {
		TransferRequest request = new TransferRequest("2", "3", "HKD", "$100");
		mvc.perform(post(Constants.TRANSACTION).accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON)
			.content(JSON.toJSONString(request)))
			.andExpect(status().is(HttpServletResponse.SC_BAD_REQUEST))
			.andExpect(jsonPath(MESSAGE, is("Bad input format")));
	}
	
	@Test
	public void testAccountManagerGetAccounts() {
		List<Account> accounts = manager.getAccounts();
		assertEquals(3, accounts.size());
	}
	
	@Test
	public void testAccountManagerGetAccountById() {
		Account account1 = manager.getAccountById("1");
		assertNotNull(account1);
		Account account2 = manager.getAccountById("4");
		assertNull(account2);
	}
	
	private static final String MESSAGE = "$.message";
}
