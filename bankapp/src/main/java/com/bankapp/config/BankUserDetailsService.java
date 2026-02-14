package com.bankapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.bankapp.entities.Employee;
import com.bankapp.service.employee.BankEmpDetailsService;

@Service
public class BankUserDetailsService implements UserDetailsService {

	@Autowired
	private BankEmpDetailsService bankEmpDetailsService;
	
	@Override
	public UserDetails loadUserByUsername(String username) {

		Employee employee = bankEmpDetailsService.findByUsername(username);
		return new SecUser(employee);
	}

}