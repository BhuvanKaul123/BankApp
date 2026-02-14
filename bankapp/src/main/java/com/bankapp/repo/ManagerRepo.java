package com.bankapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.entities.Manager;

public interface ManagerRepo extends JpaRepository<Manager, Integer> {

}
