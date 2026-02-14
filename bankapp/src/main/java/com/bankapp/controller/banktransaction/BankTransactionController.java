package com.bankapp.controller.banktransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.dto.requests.DepositRequest;
import com.bankapp.dto.requests.TransferRequest;
import com.bankapp.dto.requests.WithdrawRequest;
import com.bankapp.dto.response.BankTransactionDto;
import com.bankapp.dto.response.DepositResponse;
import com.bankapp.dto.response.TransferResponse;
import com.bankapp.dto.response.WithdrawResponse;
import com.bankapp.enums.TransactionStatus;
import com.bankapp.service.bank.BankService;
import com.bankapp.service.banktransaction.BankTransactionService;
import com.bankapp.utilities.StrictUuidConverter;
import com.bankapp.utilities.TransactionStatusHttpMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class BankTransactionController {
	
	private BankService bankService;
	private BankTransactionService bankTransactionService;
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_CLERK')")
	@PostMapping("accounts/{accountIdString}/deposits")
	public ResponseEntity<DepositResponse> deposit(@PathVariable String accountIdString, 
			@RequestBody @Valid DepositRequest depositRequest){
		
		BigDecimal amount = depositRequest.getAmount();
		UUID accountId = StrictUuidConverter.convertStringToUUID(accountIdString);
		
		BankTransactionDto bankTransactionDto = bankService.deposit(accountId, amount);
		DepositResponse depositResponse = DepositResponse.builder()
											.transactionId(bankTransactionDto.getTransactionId())
											.transactionType(bankTransactionDto.getTransactionType())
											.accountId(bankTransactionDto.getInitiatorAccountId())
											.amount(bankTransactionDto.getAmount())
											.transactionStatus(bankTransactionDto.getTransactionStatus())
											.remarks(bankTransactionDto.getFailureReason())
											.build();
		
		return ResponseEntity.status(TransactionStatusHttpMapper.map(bankTransactionDto))
								.body(depositResponse);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_CLERK')")
	@PostMapping("accounts/{accountIdString}/withdrawals")
	public ResponseEntity<WithdrawResponse> withdraw(@PathVariable String accountIdString, 
			@RequestBody @Valid WithdrawRequest withdrawRequest){
		
		BigDecimal amount = withdrawRequest.getAmount();
		UUID accountId = StrictUuidConverter.convertStringToUUID(accountIdString);
		
		BankTransactionDto bankTransactionDto = bankService.withdraw(accountId, amount);
		WithdrawResponse withdrawResponse = WithdrawResponse.builder()
											.transactionId(bankTransactionDto.getTransactionId())
											.transactionType(bankTransactionDto.getTransactionType())
											.accountId(bankTransactionDto.getInitiatorAccountId())
											.amount(bankTransactionDto.getAmount())
											.transactionStatus(bankTransactionDto.getTransactionStatus())
											.remarks(bankTransactionDto.getFailureReason())
											.build();
		
		return ResponseEntity.status(TransactionStatusHttpMapper.map(bankTransactionDto))
								.body(withdrawResponse);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_CLERK')")
	@PostMapping("accounts/{fromAccountIdString}/transfers")
	public ResponseEntity<TransferResponse> transfer(@PathVariable String fromAccountIdString, 
			@RequestBody @Valid TransferRequest transferRequest){
		
		String toAccountIdString = transferRequest.getToAccountIdString();
		BigDecimal amount = transferRequest.getAmount();
		
		UUID fromAccountId = StrictUuidConverter.convertStringToUUID(fromAccountIdString);
		UUID toAccountId = StrictUuidConverter.convertStringToUUID(toAccountIdString);
		
		BankTransactionDto bankTransactionDto = bankService.transfer(fromAccountId, toAccountId, amount);
		TransferResponse transferResponse = TransferResponse.builder()
											.transactionId(bankTransactionDto.getTransactionId())
											.transactionType(bankTransactionDto.getTransactionType())
											.fromAccountId(bankTransactionDto.getInitiatorAccountId())
											.toAccountId(bankTransactionDto.getRecieverAccountId())
											.amount(bankTransactionDto.getAmount())
											.transactionStatus(bankTransactionDto.getTransactionStatus())
											.remarks(bankTransactionDto.getFailureReason())
											.build();
		
		return ResponseEntity.status(TransactionStatusHttpMapper.map(bankTransactionDto))
								.body(transferResponse);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@PatchMapping("transactions/{transactionIdString}/approve")
	public ResponseEntity<BankTransactionDto> approveTransaction(@PathVariable String transactionIdString){
		
		UUID transactionId = StrictUuidConverter.convertStringToUUID(transactionIdString);
		BankTransactionDto bankTransactionDto = bankService.approve(transactionId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(bankTransactionDto);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@PatchMapping("transactions/{transactionIdString}/reject")
	public ResponseEntity<BankTransactionDto> rejectTransaction(@PathVariable String transactionIdString){
		
		UUID transactionId = StrictUuidConverter.convertStringToUUID(transactionIdString);
		BankTransactionDto bankTransactionDto = bankService.reject(transactionId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(bankTransactionDto);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@GetMapping("/transactions")
	public ResponseEntity<List<BankTransactionDto>> getTransactions(
	        @RequestParam(required = false) TransactionStatus status
	) {
	    List<BankTransactionDto> transactions =
	            status == null
	                ? bankTransactionService.findAllWithFetch()
	                : bankTransactionService.findByStatusWithFetch(status);

	    return ResponseEntity.status(HttpStatus.OK).body(transactions);
	}
}
