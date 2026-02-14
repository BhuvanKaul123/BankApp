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
public class WithdrawResponse {
	private UUID transactionId;
	private TransactionType transactionType;
	private UUID accountId;
	private BigDecimal amount;
	private TransactionStatus transactionStatus;
	private TransactionFailureReason remarks;
}
