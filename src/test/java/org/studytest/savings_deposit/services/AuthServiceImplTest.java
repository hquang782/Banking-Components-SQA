package org.studytest.savings_deposit.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.Customer;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;
import org.studytest.savings_deposit.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.studytest.savings_deposit.services.Impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
public class AuthServiceImplTest {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    CustomerService customerService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @Transactional
    @Rollback
    void testRegisterWithValidData() {
        // Mock customerService.getCustomerByIdentificationNumber(...) and getCustomerByBankAccountNumber(...) to return Optional.of(...)
        Mockito.when(customerService.getCustomerByIdentificationNumber(anyString())).thenReturn(Optional.of(new Customer()));
        Mockito.when(customerService.getCustomerByBankAccountNumber(anyString())).thenReturn(Optional.of(new Customer()));

        String result = authService.register(createRegisterDtoWithValidData());
        assertEquals("Registration successful", result);
    }

    private RegisterDto createRegisterDtoWithValidData() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setFullName("John Doe");
        registerDto.setAge(30);
        registerDto.setGender("Male");
        registerDto.setDob("1992-05-15");
        registerDto.setAddress("123 Main Street");
        registerDto.setEmail("john.doe@example.com");
        registerDto.setPhoneNumber("1232456789");
        registerDto.setIdentificationNumber("1234567893");
        registerDto.setBankAccountNumber("9876524321");
        registerDto.setUsername("john_doe1");
        registerDto.setPassword("password");
        return registerDto;
    }
    @Test
    void testLoginValidUser() {
        // Tạo LoginDto
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("password");

        // Tạo instance của AuthServiceImpl với các mock đã tạo
        AuthService authService = new AuthServiceImpl(customerService,accountRepository, passwordEncoder);



        Account realAccount = accountRepository.findByUsername(loginDto.getUsername()).orElse(null);
        assertNotNull(realAccount);

        // Tạo một CustomerDTO giả định
        CustomerDTO mockCustomerDTO = new CustomerDTO();
        mockCustomerDTO.setFullName("John Doe");
        mockCustomerDTO.setAccount(realAccount);
        // trả về CustomerDTO giả định
        Mockito.when(customerService.getCustomerByAccountId(realAccount.getId()))
                .thenReturn(mockCustomerDTO);



        // Gọi phương thức login
        CustomerDTO result = authService.login(loginDto);

        // Kiểm tra xem kết quả có khác null hay không
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());


        // Kiểm tra xem phương thức getCustomerByAccountId đã được gọi đến hay không
        Mockito.verify(customerService).getCustomerByAccountId(realAccount.getId());
    }
}
