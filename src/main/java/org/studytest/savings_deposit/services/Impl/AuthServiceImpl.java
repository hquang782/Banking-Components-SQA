package org.studytest.savings_deposit.services.Impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.ERole;
import org.studytest.savings_deposit.models.Role;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;
import org.studytest.savings_deposit.repositories.AccountRepository;
import org.studytest.savings_deposit.repositories.RoleRepository;
import org.studytest.savings_deposit.services.AuthService;
import org.studytest.savings_deposit.services.CustomerService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    public AuthServiceImpl(CustomerService customerService,AccountRepository accountRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
    }

    @Override
    public String login(LoginDto loginDto) {
        if (isValidUser(loginDto.getUsername(), loginDto.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid username or password";
        }
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (isUsernameAvailable(registerDto.getUsername(),registerDto.getIdentificationNumber())) {
            Account account = createAccount(registerDto.getUsername(), registerDto.getPassword());
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
            System.out.println(customerService.createCustomer(customerDTO));
            return "Registration successful";
        } else {
            return "Username already exists or identification number has bees use";
        }
    }



    private boolean isValidUser(String username, String password) {
        // Lấy tài khoản từ username
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        // Kiểm tra xem tài khoản có tồn tại và mật khẩu có khớp không
        String encodedPassword = optionalAccount.get().getPassword();
        return optionalAccount.isPresent() && passwordEncoder.matches(password, encodedPassword);
    }

    private boolean isUsernameAvailable(String username,String identificationNumber) {
        // Kiểm tra xem username có tồn tại hay không hoặc căn cước đã được sử dụng
        return !accountRepository.existsByUsername(username) &&
                customerService.getCustomerByIdentificationNumber(identificationNumber).isEmpty();
    }

    private Account createAccount(String username, String password) {
        // Tạo mới người dùng
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.valueOf("ROLE_USER")).get();
        roles.add(userRole);
        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(passwordEncoder.encode(password));
        newAccount.setRoles(roles);
        accountRepository.save(newAccount);
        return newAccount;
    }

}