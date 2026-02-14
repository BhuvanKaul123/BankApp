package com.bankapp.service.usermanagement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.annotations.LogStatus;
import com.bankapp.annotations.PerformanceTracker;
import com.bankapp.entities.Manager;
import com.bankapp.exceptions.ResourceNotFoundException;
import com.bankapp.repo.ManagerRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private ManagerRepo managerRepo;

    @Override
    @PerformanceTracker
    @LogStatus
    public Manager findById(Integer id) {
        return managerRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Manager not found with Id: " + id));
    }

    @Override
    @PerformanceTracker
    @LogStatus
    public List<Manager> findAll() {
        return managerRepo.findAll();
    }

    @Override
    @PerformanceTracker
    @LogStatus
    public Manager addManager(Manager manager) {
        return managerRepo.save(manager);
    }

    @Override
    @PerformanceTracker
    @LogStatus
    public Manager updateManager(Manager manager, Integer id) {
        Manager managerToUpdate = findById(id);
        managerToUpdate.setName(manager.getName());
        return managerToUpdate;
    }

    @Override
    @PerformanceTracker
    @LogStatus
    public void deleteManager(Manager manager) {
        managerRepo.delete(manager);
    }
}
