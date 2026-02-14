package com.bankapp.service.banktransaction;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.annotations.LogStatus;
import com.bankapp.annotations.PerformanceTracker;
import com.bankapp.dto.response.BankTransactionDto;
import com.bankapp.entities.BankTransaction;
import com.bankapp.enums.TransactionFailureReason;
import com.bankapp.enums.TransactionStatus;
import com.bankapp.exceptions.ResourceNotFoundException;
import com.bankapp.repo.BankTransactionRepo;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class BankTransactionServiceImpl implements BankTransactionService{
	
	private BankTransactionRepo bankTransactionRepo;
	
	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransaction findByTransactionId(UUID transactionId) {
		return bankTransactionRepo.findByTransactionId(transactionId).orElseThrow(
				() -> new ResourceNotFoundException("No Transaction Found with Id : " + transactionId));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@PerformanceTracker
	@LogStatus
	public BankTransaction saveTransaction(BankTransaction bankTransaction) {
		bankTransactionRepo.save(bankTransaction);
		return bankTransaction;
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public BankTransaction failAndSaveTransaction(BankTransaction bankTransaction,
												TransactionFailureReason failureReason) {
		bankTransaction.setTransactionStatus(TransactionStatus.FAILED);
		bankTransaction.setFailureReason(failureReason);
		saveTransaction(bankTransaction);
		
		return bankTransaction;
	}
	
	@Override
	@PerformanceTracker
	@LogStatus
	public List<BankTransactionDto> findAllWithFetch(){
		List<BankTransaction> allTransactions = bankTransactionRepo.findAllWithFetch();
		return allTransactions.stream().map(t-> t.toBankTransactionDto()).toList();
	}
	
	@Override
	@PerformanceTracker
	@LogStatus
	public List<BankTransactionDto> findByStatusWithFetch(TransactionStatus status){
		List<BankTransaction> allTransactions = bankTransactionRepo.findByStatusWithFetch(status);
		return allTransactions.stream().map(t -> t.toBankTransactionDto()).toList();
	}
}
