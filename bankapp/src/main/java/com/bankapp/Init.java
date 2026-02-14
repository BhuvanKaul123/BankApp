//package com.bankapp;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.bankapp.entities.Manager;
//import com.bankapp.service.employee.BankEmpDetailsService;
//
//
//@Component
//public class Init implements CommandLineRunner {
//
//	@Autowired
//	private BankEmpDetailsService userService;
//
//	@Override
//	public void run(String... args) throws Exception {
//		Manager manager = Manager.builder()
//	            .name("Demo Manager")
//	            .username("demo_mgr")
//	            .password("demo123")
//	            .roles(List.of("MANAGER", "CLERK"))
//	            .build();
//
//	    userService.addEmploye(manager);	
//	}
//}