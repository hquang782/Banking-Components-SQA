package org.studytest.savings_deposit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class confirmSaveAccount {
    @GetMapping("/confirmSA")
    public String enterAccount(){
        return "confirmAccount" ;
    }
}
