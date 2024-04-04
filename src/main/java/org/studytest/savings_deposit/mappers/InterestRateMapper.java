package org.studytest.savings_deposit.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.studytest.savings_deposit.models.InterestRate;
import org.studytest.savings_deposit.payload.InterestRateDTO;

@Component
public class InterestRateMapper {
    private final ModelMapper modelMapper;
    public InterestRateMapper(){
        this.modelMapper = new ModelMapper();
    }

    public InterestRateDTO convertToDTO(InterestRate interestRate){
        return modelMapper.map(interestRate,InterestRateDTO.class);
    }
    public InterestRate convertToEntity(InterestRateDTO interestRateDTO){
        return modelMapper.map(interestRateDTO,InterestRate.class);
    }
}
