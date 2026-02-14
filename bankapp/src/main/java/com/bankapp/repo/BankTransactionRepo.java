package com.bankapp.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankapp.entities.BankTransaction;
import com.bankapp.enums.TransactionStatus;

public interface BankTransactionRepo extends JpaRepository<BankTransaction, UUID> {
	public Optional<BankTransaction> findByTransactionId(UUID transactionId);
	
	@Query("""
			select distinct t from BankTransaction t  
			join fetch t.initiatorAccount
			left join fetch t.receiverAccount
			left join fetch t.createdBy
			where t.transactionStatus = :status
			""")
	List<BankTransaction> findByStatusWithFetch(@Param("status") TransactionStatus status);
	
	@Query("""
			select distinct t from BankTransaction t  
			join fetch t.initiatorAccount
			left join fetch t.receiverAccount
			left join fetch t.createdBy
			""")
	List<BankTransaction> findAllWithFetch();
}
