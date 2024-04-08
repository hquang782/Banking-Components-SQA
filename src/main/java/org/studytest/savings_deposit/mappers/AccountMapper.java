package org.studytest.savings_deposit.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.payload.AccountDTO;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper() {
        this.modelMapper = new ModelMapper();
    }

    public AccountDTO convertToDTO(Account account) {
        return modelMapper.map(account, AccountDTO.class);
    }

    public Account convertToEntity(AccountDTO accountDTO) {
        return modelMapper.map(accountDTO, Account.class);
    }
}