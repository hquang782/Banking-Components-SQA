package org.studytest.savings_deposit.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.studytest.savings_deposit.models.SavingsAccount;
import org.studytest.savings_deposit.payload.SavingsAccountDTO;

@Component
public class SavingsAccountMapper {
    private final ModelMapper modelMapper;
    public SavingsAccountMapper(){
        this.modelMapper = new ModelMapper();
    }

    public SavingsAccountDTO convertToDTO(SavingsAccount savingsAccount){
        return modelMapper.map(savingsAccount,SavingsAccountDTO.class);
    }
    public SavingsAccount convertToEntity(SavingsAccountDTO savingsAccountDTO){
        return modelMapper.map(savingsAccountDTO,SavingsAccount.class);
    }
}
