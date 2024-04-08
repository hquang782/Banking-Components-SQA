package org.studytest.savings_deposit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class AuthWebController {
    @GetMapping(value = {"/login", "/signin"})
    public String showLoginPage(){
        return "login";
    }
    @GetMapping(value = {"/signup", "/register"})
    public String showSignUpPage(){
        return "register";
    }

}
