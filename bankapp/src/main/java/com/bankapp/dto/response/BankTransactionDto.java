package com.bankapp.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import com.bankapp.enums.TransactionFailureReason;
import com.bankapp.enums.TransactionStatus;
import com.bankapp.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankTransactionDto {
	private UUID transactionId;
	private TransactionType transactionType;
	private TransactionStatus transactionStatus;
	private TransactionFailureReason failureReason;
	private UUID initiatorAccountId;
	private UUID recieverAccountId;
	private BigDecimal amount;
}
