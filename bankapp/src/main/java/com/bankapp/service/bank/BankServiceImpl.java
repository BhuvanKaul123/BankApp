package com.bankapp.service.bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.annotations.LogStatus;
import com.bankapp.annotations.PerformanceTracker;
import com.bankapp.dto.response.BankTransactionDto;
import com.bankapp.entities.Account;
import com.bankapp.entities.BankTransaction;
import com.bankapp.enums.TransactionFailureReason;
import com.bankapp.enums.TransactionStatus;
import com.bankapp.enums.TransactionType;
import com.bankapp.exceptions.InvalidRequestException;
import com.bankapp.service.account.AccountService;
import com.bankapp.service.banktransaction.BankTransactionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class BankServiceImpl implements BankService {
	
	private AccountService accountService;
	private BankTransactionService bankTransactionService;
	
	private boolean isManager() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    return authentication.getAuthorities().stream()
	            .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransactionDto deposit(UUID accountId, BigDecimal amount) {
		
		Account account = accountService.findEntityById(accountId);
		
		BankTransaction bankTransaction =  BankTransaction.builder()
								.transactionType(TransactionType.CREDIT)
								.timestamp(LocalDateTime.now())
								.amount(amount)
								.initiatorAccount(account)
								.transactionStatus(TransactionStatus.PENDING)
								.build();
		
		account.setBalance(account.getBalance().add(amount));
		accountService.updateAccountEntity(account, accountId);
		
		bankTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
		bankTransactionService.saveTransaction(bankTransaction);
		
		return bankTransaction.toBankTransactionDto();
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransactionDto withdraw(UUID accountId, BigDecimal amount) {		
		Account account = accountService.findEntityById(accountId);
		
		BankTransaction bankTransaction = BankTransaction.builder()
											.transactionType(TransactionType.DEBIT)
											.timestamp(LocalDateTime.now())
											.amount(amount)
											.initiatorAccount(account)
											.transactionStatus(TransactionStatus.PENDING)
											.build();
		
		if(!accountService.hasSufficientBalance(account, amount)) {
			bankTransaction = bankTransactionService.failAndSaveTransaction(bankTransaction, 
								TransactionFailureReason.INSUFFICIENT_BALANCE);
			return bankTransaction.toBankTransactionDto();
		}
		
		int withdrawApprovalComparisionResult = 
				amount.compareTo(BigDecimal.valueOf(200_000));
		
		boolean autoApprove = withdrawApprovalComparisionResult <= 0 || isManager();

		if(autoApprove) {
			
			account.setBalance(account.getBalance().subtract(amount));
			accountService.updateAccountEntity(account, accountId);
			
			bankTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
		}
		
		bankTransactionService.saveTransaction(bankTransaction);
		return bankTransaction.toBankTransactionDto();
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransactionDto transfer(UUID fromAccountId, UUID toAccountId, BigDecimal amount) {
		Account fromAccount = accountService.findEntityById(fromAccountId);
		Account toAccount = accountService.findEntityById(toAccountId);
		
		BankTransaction bankTransaction = BankTransaction.builder()
											.transactionType(TransactionType.TRANSFER)
											.timestamp(LocalDateTime.now())
											.amount(amount)
											.initiatorAccount(fromAccount)
											.receiverAccount(toAccount)
											.transactionStatus(TransactionStatus.PENDING)
											.build();
		
		// amount requested is more than available balance
		if(!accountService.hasSufficientBalance(fromAccount, amount)) {
			bankTransaction = bankTransactionService.failAndSaveTransaction(bankTransaction, 
					TransactionFailureReason.INSUFFICIENT_BALANCE);
			return bankTransaction.toBankTransactionDto();
		}
		
		int transferApprovalComparisionResult = 
				amount.compareTo(BigDecimal.valueOf(200_000));
		
		boolean autoApprove = transferApprovalComparisionResult <= 0 || isManager();

		if(autoApprove) {
			fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
			toAccount.setBalance(toAccount.getBalance().add(amount));
			bankTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
			accountService.updateAccountEntity(fromAccount, fromAccountId);
			accountService.updateAccountEntity(toAccount, toAccountId);
		}
		
		
		bankTransactionService.saveTransaction(bankTransaction);
		
		return bankTransaction.toBankTransactionDto();
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransactionDto approve(UUID transactionId) {
		BankTransaction bankTransaction = bankTransactionService.findByTransactionId(transactionId);
		
		if(bankTransaction.getTransactionStatus() != TransactionStatus.PENDING) {
			throw new InvalidRequestException("Transaction is already completed, cannot approve");
		}
		
		Account fromAccount = bankTransaction.getInitiatorAccount();
		Account toAccount = bankTransaction.getReceiverAccount();
		
		// amount requested is more than available balance
		if(!accountService.hasSufficientBalance(fromAccount, bankTransaction.getAmount())) {
			bankTransaction = bankTransactionService.failAndSaveTransaction(bankTransaction, 
					TransactionFailureReason.INSUFFICIENT_BALANCE);
			return bankTransaction.toBankTransactionDto();
		}
		
		if(bankTransaction.getTransactionType() == TransactionType.DEBIT) {
			fromAccount.setBalance(fromAccount.getBalance().subtract(bankTransaction.getAmount()));
			accountService.updateAccountEntity(fromAccount, fromAccount.getId());
			bankTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
		}
		else if(bankTransaction.getTransactionType() == TransactionType.TRANSFER) {	
			fromAccount.setBalance(fromAccount.getBalance().subtract(bankTransaction.getAmount()));
			toAccount.setBalance(toAccount.getBalance().add(bankTransaction.getAmount()));
			accountService.updateAccountEntity(fromAccount, fromAccount.getId());
			accountService.updateAccountEntity(toAccount, toAccount.getId());
			bankTransaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
		}
		
		bankTransactionService.saveTransaction(bankTransaction);
		return bankTransaction.toBankTransactionDto();
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransactionDto reject(UUID transactionId) {
		BankTransaction bankTransaction = bankTransactionService.findByTransactionId(transactionId);
		
		if(bankTransaction.getTransactionStatus() != TransactionStatus.PENDING) {
			throw new InvalidRequestException("Transaction is already completed, cannot approve");
		}
		
		bankTransaction = bankTransactionService.failAndSaveTransaction(bankTransaction, 
				TransactionFailureReason.MANAGER_REJECTED);

		return bankTransaction.toBankTransactionDto();
	}
}
