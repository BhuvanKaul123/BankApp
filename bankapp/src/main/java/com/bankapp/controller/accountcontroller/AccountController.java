package com.bankapp.controller.accountcontroller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankapp.dto.requests.AccountRequest;
import com.bankapp.dto.response.AccountResponse;
import com.bankapp.service.account.AccountService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "accounts")
@AllArgsConstructor
public class AccountController {

	private AccountService accountService;
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@GetMapping
	public ResponseEntity<List<AccountResponse>> getAll() {
		return ResponseEntity.ok(accountService.findAll());
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@GetMapping("/{id}")
	public ResponseEntity<AccountResponse> getById(@PathVariable String id) {
		return ResponseEntity.ok(accountService.findById(id));
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@PostMapping
	public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
		AccountResponse response = accountService.createAccount(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@PutMapping("/{idString}")
	public ResponseEntity<AccountResponse> update(@PathVariable String idString, 
			@Valid @RequestBody AccountRequest request) {
		AccountResponse response = accountService.updateAccount(request, idString);
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
