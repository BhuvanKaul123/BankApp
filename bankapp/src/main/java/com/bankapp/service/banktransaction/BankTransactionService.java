package com.bankapp.service.banktransaction;

import java.util.List;
import java.util.UUID;

import com.bankapp.dto.response.BankTransactionDto;
import com.bankapp.entities.BankTransaction;
import com.bankapp.enums.TransactionFailureReason;
import com.bankapp.enums.TransactionStatus;

public interface BankTransactionService {
	BankTransaction findByTransactionId(UUID transactionId);
	BankTransaction saveTransaction(BankTransaction bankTransaction);
	BankTransaction failAndSaveTransaction(BankTransaction bankTransaction,
											TransactionFailureReason failureReason);
	public List<BankTransactionDto> findAllWithFetch();
	public List<BankTransactionDto> findByStatusWithFetch(TransactionStatus status);
}
