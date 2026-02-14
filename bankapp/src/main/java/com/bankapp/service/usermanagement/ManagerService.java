package com.bankapp.service.usermanagement;

import java.util.List;

import com.bankapp.entities.Manager;

public interface ManagerService {

    Manager findById(Integer id);
    List<Manager> findAll();
    Manager addManager(Manager manager);
    Manager updateManager(Manager manager, Integer id);
    void deleteManager(Manager manager);
}
