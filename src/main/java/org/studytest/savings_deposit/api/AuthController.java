package org.studytest.savings_deposit.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studytest.savings_deposit.payload.CustomerDTO;
import org.studytest.savings_deposit.payload.LoginDto;
import org.studytest.savings_deposit.payload.RegisterDto;
import org.studytest.savings_deposit.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<CustomerDTO> login(@RequestBody LoginDto loginDto){
        CustomerDTO response = authService.login(loginDto);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.ok(response);
        }
    }
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        if(response.equals("Registration successful")) return new ResponseEntity<>(response, HttpStatus.CREATED);
        else return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/verifyPassword")
    public ResponseEntity<String> verifyPassword(@RequestBody LoginDto loginDto ){
        CustomerDTO response = authService.login(loginDto);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } else {
            return ResponseEntity.ok("Done");
        }
    }
}
