package com.bankapp.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bankapp.entities.Account;

public interface AccountRepo extends JpaRepository<Account, UUID>{	
	@Query("select distinct a from Account a left join fetch a.transactions")
	List<Account> findAll();
}
