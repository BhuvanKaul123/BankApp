package com.bankapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.entities.Employee;
import java.util.Optional;


public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	Optional<Employee> findByUsername(String username);
}
