package org.studytest.savings_deposit.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;

import org.studytest.savings_deposit.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
