package com.bankappv2.mapper;

import org.mapstruct.Mapper;

import com.bankappv2.dto.AccountRequest;
import com.bankappv2.dto.AccountResponse;
import com.bankappv2.entity.Account;

@Mapper(componentModel = "spring") 
public interface AccountMapper {
	    AccountResponse toResponse(Account account);
	    Account toEntity(AccountRequest request);
}
