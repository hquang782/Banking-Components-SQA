package org.studytest.savings_deposit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnterSavingAccount {
    @GetMapping("/enter")
    public String enterAccount(){
        return "savingAccount" ;
    }
}
