package org.studytest.savings_deposit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class calculateInterest {
    @GetMapping("/calculate-interest")
    public String showCalculatePage(){
        return "tinhlai";
    }
}
