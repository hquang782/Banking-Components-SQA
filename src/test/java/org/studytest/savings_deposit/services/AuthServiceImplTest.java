package org.studytest.savings_deposit.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;
import org.studytest.savings_deposit.repositories.AccountRepository;
import org.studytest.savings_deposit.services.Impl.AuthServiceImpl;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AuthServiceImplTest {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomerService customerService;

    @Mock
    CustomerService customerServiceMock;
    @BeforeEach
    public void setUp() {
    }

    @Test
    @Transactional
    @Rollback
    void testRegisterWithValidData() {
        String result = authService.register(createRegisterDtoWithValidData());
        assertEquals("Registration successful", result);


    }

    @Test
    @Transactional
    @Rollback
    void testRegisterWithInValidData(){
        String resultFail = authService.register(createRegisterDtoWithInValidData());
        assertEquals("Username, identification number, or bank account number already exists", resultFail);
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
    private RegisterDto createRegisterDtoWithInValidData(){
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
        registerDto.setUsername("jane_doe");
        registerDto.setPassword("password");
        return registerDto;
    }

    @Test
    void testLoginValidUser() {
        // Tạo LoginDto
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("password");
        AuthService authService = new AuthServiceImpl(customerServiceMock,accountRepository, passwordEncoder);
        Account realAccount = accountRepository.findByUsername(loginDto.getUsername()).orElse(null);
        assertNotNull(realAccount);
        // Tạo một CustomerDTO giả định
        CustomerDTO mockCustomerDTO = new CustomerDTO();
        mockCustomerDTO.setFullName("John Doe");
        mockCustomerDTO.setAccount(realAccount);
        // trả về CustomerDTO giả định
        Mockito.when(customerServiceMock.getCustomerByAccountId(realAccount.getId()))
                .thenReturn(mockCustomerDTO);
        // Gọi phương thức login
        CustomerDTO result = authService.login(loginDto);
        // Kiểm tra xem kết quả có khác null hay không
        assertNotNull(result);
        //so sánh kết quả login với giả định
        assertEquals(mockCustomerDTO.getFullName(), result.getFullName());
        // Kiểm tra xem phương thức getCustomerByAccountId đã được gọi đến hay không
        Mockito.verify(customerServiceMock).getCustomerByAccountId(realAccount.getId());
    }

    @Test
    void testLoginInValidPassword() {
        // Tạo LoginDto với mật khẩu sai
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("password3");
        AuthService authService = new AuthServiceImpl(customerService,accountRepository, passwordEncoder);
        Account realAccount = accountRepository.findByUsername(loginDto.getUsername()).orElse(null);
        // lấy được tài khoản bởi username thành công
        assertNotNull(realAccount);
        CustomerDTO result = authService.login(loginDto);
        // Kiểm tra xem kết quả có null hay không
        assertNull(result);
    }

    @Test
    void testLoginInValidUser(){
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("quang");
        loginDto.setPassword("password3");
        AuthService authService = new AuthServiceImpl(customerService,accountRepository, passwordEncoder);
        Account realAccount = accountRepository.findByUsername(loginDto.getUsername()).orElse(null);
        // không lấy được tài khoản bởi username sai
        assertNull(realAccount);

        CustomerDTO result = authService.login(loginDto);
        // Kiểm tra xem kết quả có null hay không
        assertNull(result);
    }
}
