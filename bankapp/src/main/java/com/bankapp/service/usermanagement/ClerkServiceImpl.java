package com.bankapp.service.usermanagement;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.annotations.LogStatus;
import com.bankapp.annotations.PerformanceTracker;
import com.bankapp.dto.requests.ClerkRequest;
import com.bankapp.dto.response.ClerkResponse;
import com.bankapp.entities.Clerk;
import com.bankapp.entities.Manager;
import com.bankapp.exceptions.ResourceNotFoundException;
import com.bankapp.mapper.ClerkMapper;
import com.bankapp.repo.ClerkRepo;
import com.bankapp.service.employee.BankEmpDetailsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ClerkServiceImpl implements ClerkService {
	
	private ClerkRepo clerkRepo;
	private ClerkMapper clerkMapper;
	private ManagerService managerService;
	private BankEmpDetailsService bankEmpDetailsService;
	
	private Clerk findEntityById(Integer id) {
		return clerkRepo.findById(id).orElseThrow(
				()-> new ResourceNotFoundException("Clerk not found with Id: " + id));
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public ClerkResponse findById(Integer id) {
		Clerk clerk = findEntityById(id);
		
		return clerkMapper.toResponse(clerk);
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public List<ClerkResponse> findAll() {
		List<Clerk> clerks = clerkRepo.findAll();
		return clerks.stream().map(clerkMapper::toResponse).toList();
	}
	

	@Override
	@PerformanceTracker
	@LogStatus
	public ClerkResponse updateClerk(ClerkRequest clerk, Integer id) {
		Clerk clerkToUpdate = findEntityById(id);
		clerkToUpdate.setName(clerk.getName());
		clerkToUpdate.setManager(managerService.findById(clerk.getManagerId()));
		
		clerkRepo.save(clerkToUpdate);
		return clerkMapper.toResponse(clerkToUpdate);
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public ClerkResponse addClerk(ClerkRequest clerkReq) {
		Clerk clerk = clerkMapper.toEntity(clerkReq);
		Manager manager = managerService.findById(clerkReq.getManagerId());
		clerk.setManager(manager);
		return clerkMapper.toResponse(bankEmpDetailsService.addClerk(clerk));
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public void deleteClerk(Integer clerkId) {
		Clerk clerk = findEntityById(clerkId);
		clerkRepo.delete(clerk);
	}

	@Override
	@PerformanceTracker
	@LogStatus
	public List<ClerkResponse> findAllWithManager() {
		return clerkRepo.findAllWithManager().stream().map(clerkMapper::toResponse).toList();
	}

	@Override
	public ClerkResponse findByIdWithManager(Integer id) {
		Clerk clerk = clerkRepo.findByIdWithManager(id).orElseThrow(
				()-> new ResourceNotFoundException("Clerk not found with Id: " + id));
		
		return clerkMapper.toResponse(clerk);
	}
}
