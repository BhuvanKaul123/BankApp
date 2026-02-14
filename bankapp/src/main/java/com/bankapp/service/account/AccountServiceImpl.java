package com.bankapp.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.annotations.LogStatus;
import com.bankapp.annotations.PerformanceTracker;
import com.bankapp.dto.requests.AccountRequest;
import com.bankapp.dto.response.AccountResponse;
import com.bankapp.entities.Account;
import com.bankapp.exceptions.ResourceNotFoundException;
import com.bankapp.mapper.AccountMapper;
import com.bankapp.repo.AccountRepo;
import com.bankapp.utilities.StrictUuidConverter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
	
	private AccountRepo accountRepo;
	private AccountMapper accountMapper;
	
	@Override
	@PerformanceTracker
	@LogStatus
	public Account findEntityById(UUID id) {
		return accountRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("No Account Found With ID: " + id));
	}
	
	@Override
	@PerformanceTracker
	@LogStatus
	public AccountResponse findById(String idString) {
		UUID id = StrictUuidConverter.convertStringToUUID(idString);
		Account account = findEntityById(id);
		return accountMapper.toResponse(account);
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public AccountResponse createAccount(AccountRequest accountReq) {
		Account account = accountMapper.toEntity(accountReq);
		accountRepo.save(account);
		return accountMapper.toResponse(account);
	}	

	@Override
	@PerformanceTracker
	@LogStatus
	public AccountResponse updateAccount(AccountRequest accountReq, String idString) {
		UUID id = StrictUuidConverter.convertStringToUUID(idString);
		Account account = accountMapper.toEntity(accountReq);
		Account accountToUpdate = findEntityById(id);
		accountToUpdate.setHolderName(account.getHolderName());
		accountToUpdate.setBalance(account.getBalance());
		accountRepo.save(accountToUpdate);
		return accountMapper.toResponse(accountToUpdate);
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public void deleteAccount(UUID AccountId) {
		Account accountToDelete = findEntityById(AccountId);
		accountRepo.delete(accountToDelete);
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public List<AccountResponse> findAll() {
	    return accountRepo.findAll().stream().map(accountMapper::toResponse).toList();
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public boolean hasSufficientBalance(Account account, BigDecimal amount) {
		int balanceAvailableComparisionResult = account.getBalance().compareTo(amount);
		
		// not enough balance
		if(balanceAvailableComparisionResult < 0) {
			return false;
		}
		return true;
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public Account updateAccountEntity(Account account, UUID id) {
		Account accountToUpdate = findEntityById(id);
		accountToUpdate.setHolderName(account.getHolderName());
		accountToUpdate.setBalance(account.getBalance());
		accountRepo.save(accountToUpdate);
		return accountToUpdate;
	}
}

