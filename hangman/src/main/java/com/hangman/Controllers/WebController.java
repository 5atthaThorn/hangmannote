package com.hangman.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController{
    //private int UserID = 0;
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    @GetMapping("/hangmannote")
    public String hangmanNote() {
        return "hangmannote";
    }
    
}