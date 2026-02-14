package com.bankapp.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.bankapp.dto.response.BankTransactionDto;
import com.bankapp.enums.TransactionFailureReason;
import com.bankapp.enums.TransactionStatus;
import com.bankapp.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="transactions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID transactionId;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	private LocalDateTime timestamp;
	
	private BigDecimal amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initiator_account_id")
	private Account initiatorAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_account_id", nullable = true)
	private Account receiverAccount;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;	
	
	@Enumerated(EnumType.STRING)
	private TransactionFailureReason failureReason;
	
	@ManyToOne
	@JoinColumn(name = "created_by_emp_id")
	private Employee createdBy;
	
	@ManyToOne
	@JoinColumn(name = "approved_by_manager_id")
	private Manager approvedBy;
	
	public BankTransactionDto toBankTransactionDto() {
		return BankTransactionDto.builder()
				.transactionId(transactionId)
				.transactionStatus(transactionStatus)
				.transactionType(transactionType)
				.failureReason(failureReason)
				.initiatorAccountId(initiatorAccount.getId())
				.recieverAccountId(receiverAccount==null ? null : receiverAccount.getId())
				.amount(amount)
				.build();
	}
}

