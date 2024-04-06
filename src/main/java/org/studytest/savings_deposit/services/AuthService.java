package org.studytest.savings_deposit.services;

import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;

public interface AuthService {
    CustomerDTO login(LoginDto loginDto);

    String register(RegisterDto registerDto) ;
}
