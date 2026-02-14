package com.bankapp.controller.clerkcontroller;

import java.util.List;

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

import com.bankapp.dto.requests.ClerkRequest;
import com.bankapp.dto.response.ClerkResponse;
import com.bankapp.service.usermanagement.ClerkService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("clerks")
@AllArgsConstructor
public class ClerkManagementController {
	
	private ClerkService clerkService;
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@PostMapping
    public ResponseEntity<ClerkResponse> createClerk(@Valid @RequestBody ClerkRequest clerkRequest) {
		ClerkResponse response = clerkService.addClerk(clerkRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@GetMapping
    public ResponseEntity<List<ClerkResponse>> getAllClerks() {
        List<ClerkResponse> clerks = clerkService.findAllWithManager();
        return ResponseEntity.ok(clerks);
    }
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@GetMapping("/{id}")
    public ResponseEntity<ClerkResponse> getClerkById(@PathVariable Integer id) {
        ClerkResponse clerk = clerkService.findById(id);
        return ResponseEntity.ok(clerk);
    }
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@PutMapping("/{id}")
    public ResponseEntity<ClerkResponse> updateClerk(
            @PathVariable Integer id,
            @Valid @RequestBody ClerkRequest clerkRequest) {
        ClerkResponse updatedClerk = clerkService.updateClerk(clerkRequest, id);
        return ResponseEntity.ok(updatedClerk);
    }
	
	@PreAuthorize("hasAuthority('ROLE_MANAGER')")
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClerk(@PathVariable Integer id) {
        clerkService.deleteClerk(id);
        return ResponseEntity.noContent().build();
    }
	
}
