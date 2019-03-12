package com.example.transfermoney.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transfermoney.model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
