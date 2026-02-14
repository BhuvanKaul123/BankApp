package com.bankapp.service.usermanagement;

import java.util.List;

import com.bankapp.dto.requests.ClerkRequest;
import com.bankapp.dto.response.ClerkResponse;

public interface ClerkService {
	ClerkResponse findById(Integer id);
	List<ClerkResponse> findAll();
	ClerkResponse updateClerk(ClerkRequest clerk, Integer id);
	ClerkResponse addClerk(ClerkRequest clerk);
	void deleteClerk(Integer clerkId);
	List<ClerkResponse> findAllWithManager();
	ClerkResponse findByIdWithManager(Integer id);
}
