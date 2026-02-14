package com.bankapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bankapp.dto.requests.ClerkRequest;
import com.bankapp.dto.response.ClerkResponse;
import com.bankapp.entities.Clerk;

@Mapper(componentModel = "spring",
		uses = ManagerMapper.class)
public interface ClerkMapper {
	ClerkResponse toResponse(Clerk clerk);

	@Mapping(target = "manager", ignore = true)
	Clerk toEntity(ClerkRequest request);
}
