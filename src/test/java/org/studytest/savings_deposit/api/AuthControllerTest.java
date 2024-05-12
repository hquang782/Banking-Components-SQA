package org.studytest.savings_deposit.api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @Test
    @Rollback
    public void testLogin_Success() {
        LoginDto loginDto = new LoginDto("test", "password");
        ResponseEntity<CustomerDTO> response = authController.login(loginDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    @Rollback
    public void testLogin_Unauthorized() {
        LoginDto loginDto = new LoginDto("invalid_username", "invalid_password");
        ResponseEntity<CustomerDTO> response = authController.login(loginDto);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    @Transactional
    @Rollback
    public void testRegister_Success() {
        RegisterDto registerDto = createRegisterDtoWithValidData();
        ResponseEntity<String> response = authController.register(registerDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Registration successful",response.getBody());
    }


    @Test
    @Transactional
    @Rollback
    public void testRegister_Failure() {
        RegisterDto registerDto = createRegisterDtoWithInValidData();
        ResponseEntity<String> response = authController.register(registerDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username, identification number, or bank account number already exists",response.getBody());
    }

    @Test
    @Rollback
    public void testVerify_Success() {
        LoginDto loginDto = new LoginDto("test", "password");
        ResponseEntity<CustomerDTO> response = authController.login(loginDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    @Rollback
    public void testVerify_Unauthorized() {
        LoginDto loginDto = new LoginDto("invalid_username", "invalid_password");
        ResponseEntity<CustomerDTO> response = authController.login(loginDto);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
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
}
