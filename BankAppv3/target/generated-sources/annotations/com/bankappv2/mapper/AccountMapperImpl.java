package com.bankappv2.mapper;

import com.bankappv2.dto.AccountRequest;
import com.bankappv2.dto.AccountResponse;
import com.bankappv2.entity.Account;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-14T23:07:10+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountResponse toResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setBalance( account.getBalance() );
        accountResponse.setEmail( account.getEmail() );
        accountResponse.setId( account.getId() );
        accountResponse.setName( account.getName() );
        accountResponse.setPhone( account.getPhone() );

        return accountResponse;
    }

    @Override
    public Account toEntity(AccountRequest request) {
        if ( request == null ) {
            return null;
        }

        Account account = new Account();

        account.setBalance( request.getBalance() );
        account.setEmail( request.getEmail() );
        account.setName( request.getName() );
        account.setPhone( request.getPhone() );

        return account;
    }
}
