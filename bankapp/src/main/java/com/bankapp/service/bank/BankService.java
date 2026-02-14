package com.bankapp.service.bank;

import java.math.BigDecimal;
import java.util.UUID;

import com.bankapp.dto.response.BankTransactionDto;

public interface BankService {
	public BankTransactionDto deposit(UUID accountId, BigDecimal amount);
	public BankTransactionDto withdraw(UUID accountId, BigDecimal amount);
	public BankTransactionDto transfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount);
	public BankTransactionDto approve(UUID transactionId);
	public BankTransactionDto reject(UUID transactionId);
}
