package org.studytest.savings_deposit.services.Impl;

import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;
import org.studytest.savings_deposit.repositories.AccountRepository;
import org.studytest.savings_deposit.services.AuthService;
import org.studytest.savings_deposit.services.CustomerService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    public AuthServiceImpl(CustomerService customerService,AccountRepository accountRepository,PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }


    @Override
    public CustomerDTO login(LoginDto loginDto) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(loginDto.getUsername());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            String encodedPassword = account.getPassword();
            System.out.println(encodedPassword);
            if (passwordEncoder.matches(loginDto.getPassword(), encodedPassword)) {
                return customerService.getCustomerByAccountId(account.getId());
            }
            else System.out.println("sai");
        }
        else System.out.println("not found");
        return null;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (isUsernameAvailable(registerDto.getUsername(),registerDto.getIdentificationNumber(),registerDto.getBankAccountNumber())) {
            Account account = createAccount(registerDto.getUsername(), registerDto.getPassword());
            CustomerDTO customerDTO = getCustomerDTO(registerDto, account);
            System.out.println(customerService.createCustomer(customerDTO));
            return "Registration successful";
        } else {
            return "Username, identification number, or bank account number already exists";
        }
    }

    private static CustomerDTO getCustomerDTO(RegisterDto registerDto, Account account) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName(registerDto.getFullName());
        customerDTO.setAge(registerDto.getAge());
        customerDTO.setAddress(registerDto.getAddress());
        customerDTO.setGender(registerDto.getGender());
        customerDTO.setDob(registerDto.getDob());
        customerDTO.setEmail(registerDto.getEmail());
        customerDTO.setPhoneNumber(registerDto.getPhoneNumber());
        customerDTO.setIdentificationNumber(registerDto.getIdentificationNumber());
        customerDTO.setBankAccountNumber(registerDto.getBankAccountNumber());
        customerDTO.setAccount(account);
        return customerDTO;
    }

    private boolean isUsernameAvailable(String username,String identificationNumber,String bankAccountNumber) {
        // Kiểm tra xem username có tồn tại hay không hoặc căn cước đã được sử dụng
        return !accountRepository.existsByUsername(username) &&
                customerService.getCustomerByIdentificationNumber(identificationNumber).isEmpty()&&customerService.getCustomerByBankAccountNumber(bankAccountNumber).isEmpty();
    }

    private Account createAccount(String username, String password) {
        // Tạo mới người dùng
        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(passwordEncoder.encode(password));
        accountRepository.save(newAccount);
        return newAccount;
    }

}