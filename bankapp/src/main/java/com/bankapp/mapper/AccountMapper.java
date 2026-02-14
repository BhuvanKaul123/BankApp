package com.bankapp.mapper;

import org.mapstruct.Mapper;

import com.bankapp.dto.requests.AccountRequest;
import com.bankapp.dto.response.AccountResponse;
import com.bankapp.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toResponse(Account account);
    Account toEntity(AccountRequest request);
}
