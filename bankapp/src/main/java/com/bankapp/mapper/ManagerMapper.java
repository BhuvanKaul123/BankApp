package com.bankapp.mapper;

import org.mapstruct.Mapper;

import com.bankapp.dto.requests.ManagerRequest;
import com.bankapp.dto.response.ManagerResponse;
import com.bankapp.entities.Manager;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    ManagerResponse toResponse(Manager manager);
	Manager toEntity(ManagerRequest request);
}
