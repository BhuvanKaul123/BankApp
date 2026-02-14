package com.bankapp.service.employee;

import com.bankapp.entities.Clerk;
import com.bankapp.entities.Employee;
import com.bankapp.entities.Manager;

public interface BankEmpDetailsService {
	Employee findByUsername(String username);
	Clerk addClerk(Clerk clerk);
	Manager addManager(Manager clerk);
}
