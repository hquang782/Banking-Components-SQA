package org.studytest.savings_deposit.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class savingsAccountWebController {
    @GetMapping("/savings-list")
    public String showSavingsListPage() {
        return "savingsList";
    }
}
