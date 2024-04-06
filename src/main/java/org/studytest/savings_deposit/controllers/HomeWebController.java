package org.studytest.savings_deposit.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {
    @GetMapping(value = {"/home","/"})
    public String showHomePage(){
        return "index";
    }
}
