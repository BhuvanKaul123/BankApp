package com.bankapp.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.bankapp.dto.requests.AccountRequest;
import com.bankapp.dto.response.AccountResponse;
import com.bankapp.entities.Account;

public interface AccountService {
	public AccountResponse findById(String idString);
	public AccountResponse createAccount(AccountRequest accountReq);
	public AccountResponse updateAccount(AccountRequest accountReq, String idString);
	public void deleteAccount(UUID AccountId);
	public List<AccountResponse> findAll();
	public boolean hasSufficientBalance(Account account, BigDecimal amount);
	public Account findEntityById(UUID id);
	public Account updateAccountEntity(Account account, UUID id);
	
}
