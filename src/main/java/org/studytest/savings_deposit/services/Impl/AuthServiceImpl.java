package org.studytest.savings_deposit.services.Impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studytest.savings_deposit.models.Account;
import org.studytest.savings_deposit.models.ERole;
import org.studytest.savings_deposit.models.Role;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;
import org.studytest.savings_deposit.repositories.AccountRepository;
import org.studytest.savings_deposit.repositories.RoleRepository;
import org.studytest.savings_deposit.services.AuthService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    public AuthServiceImpl(AccountRepository accountRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        // Triển khai logic xác thực người dùng ở đây
        // Ví dụ:
        if (isValidUser(loginDto.getUsername(), loginDto.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid username or password";
        }
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (isUsernameAvailable(registerDto.getUsername())) {
            createUser(registerDto.getUsername(), registerDto.getPassword());
            return "Registration successful";
        } else {
            return "Username already exists";
        }
    }



    private boolean isValidUser(String username, String password) {
        // Lấy tài khoản từ username
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        // Kiểm tra xem tài khoản có tồn tại và mật khẩu có khớp không
        String encodedPassword = optionalAccount.get().getPassword();
        return optionalAccount.isPresent() && passwordEncoder.matches(password, encodedPassword);
    }

    private boolean isUsernameAvailable(String username) {
        // Kiểm tra xem username có tồn tại hay không
        return !accountRepository.existsByUsername(username);
    }

    private void createUser(String username, String password) {
        // Tạo mới người dùng
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.valueOf("ROLE_USER")).get();
        roles.add(userRole);
        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(passwordEncoder.encode(password));
        accountRepository.save(newAccount);
    }
}