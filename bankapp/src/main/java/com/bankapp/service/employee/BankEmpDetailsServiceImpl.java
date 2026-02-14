package com.bankapp.service.employee;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.entities.Clerk;
import com.bankapp.entities.Employee;
import com.bankapp.entities.Manager;
import com.bankapp.exceptions.ResourceNotFoundException;
import com.bankapp.exceptions.UsernameAlreadyExistsExceptions;
import com.bankapp.repo.EmployeeRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class BankEmpDetailsServiceImpl implements BankEmpDetailsService {

	private final EmployeeRepo employeeRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Employee findByUsername(String username) {
		return employeeRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("No Employee with username: " + username));
	}

	@Override
	public Manager addManager(Manager manager) {
		manager.setRoles(List.of("MANAGER", "CLERK"));
		employeeRepo.findByUsername(manager.getUsername()).ifPresent(u -> {
			throw new UsernameAlreadyExistsExceptions("Username already exists: " + manager.getUsername());
		});

		manager.setPassword(passwordEncoder.encode(manager.getPassword()));

		return employeeRepo.save(manager);
	}
	
	@Override
    public Clerk addClerk(Clerk clerk) {
		clerk.setRoles(List.of("CLERK"));
        employeeRepo.findByUsername(clerk.getUsername()).ifPresent(u -> {
            throw new UsernameAlreadyExistsExceptions(
                "Username already exists: " + clerk.getUsername());
        });

        clerk.setPassword(passwordEncoder.encode(clerk.getPassword()));

        return employeeRepo.save(clerk);
    }
}