package com.hangman.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hangman.Entities.Users;
import com.hangman.Repositories.UsersRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController{
    
    private Users currentUser = null;


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    UsersRepository usersRepository; 
    @RequestMapping("/")
    public String index(){
        if (isLogin()){
            return "index";
        }
        return "redirect:/login";
    }
    
    @GetMapping("/hangmannote")
    public String hangmanNote() {
        if (isLogin()){
            return "hangmannote";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "loginpage";
    }

    @PostMapping("/login")
    public String getLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        Iterable<Users> usersList = usersRepository.findAll();
        for (var user: usersList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                currentUser = user;
                sendUser();
                return "redirect:/";
            }
        }
        return "loginpage";
    }

    
    @GetMapping("/register")
    public String register(){
        return "registerpage";
    }
    

    @PostMapping("/register")
    public String getRegister(@RequestParam("regisusername") String username, @RequestParam("regispassword") String password, @RequestParam("regisrepassword") String repassword) {
        if (!password.equals(repassword)){
            return "redirect:/register";
        }
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password);
        usersRepository.save(newUser);
        return "redirect:/";
    }

    public boolean isLogin(){
        if (currentUser != null){
            return true;
        }
        return false;
    }

    public void sendUser(){
        System.out.println("test");
        restTemplate.postForObject("http://localhost:8080/api/getusersession", currentUser, String.class);
        System.out.println("Data sent to API successfully!");
    }

    @GetMapping("/signout")
    public String signout() {
        currentUser = null;
        return "redirect:/login";
    }

    @GetMapping("/hangmanchord")
    public String hangmanchord(){
        if (isLogin()){
            return "hangmanchord";
        }
        return "redirect:/login";
    }
    

}