package com.hangman.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hangman.Entities.Scores;
import com.hangman.Entities.Users;
import com.hangman.Repositories.ScoresRepository;
import com.hangman.Repositories.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ScoresRepository scoresRepository;

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "index";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "loginpage";
    }

    @GetMapping("/hangmannotestart")
    public String hangmanNoteStart(HttpSession session) {
        if (isLogin(session)) {
            return "hangmannotestart";
        }
        return "redirect:/login";
    }
    
    @GetMapping("/hangmannote")
    public String hangmanNote(HttpSession session) {
        if (isLogin(session)) {
            return "hangmannote";
        }
        return "redirect:/login";
    }

    @GetMapping("/hangmanchordstart")
    public String hangmanChordStart(HttpSession session) {
        if (isLogin(session)) {
            return "hangmanchordstart";
        }
        return "redirect:/login";
    }

    @GetMapping("/hangmanchord")
    public String hangmanChord(HttpSession session) {
        if (isLogin(session)) {
            return "hangmanchord";
        }
        return "redirect:/login";
    }

    //รับค่าจากฟอร์ม log in เช็คว่า ถูกมั้ย
    @PostMapping("/login")
    public String getLogin(@RequestParam("username") String username,
            @RequestParam("password") String password, HttpSession session) {
        Iterable<Users> usersList = usersRepository.findAll();
        for (var user : usersList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                sendUser(user);
                return "redirect:/";
            }
        }
        return "loginpage";
    }

    @GetMapping("/register")
    public String register() {
        return "registerpage";
    }

    @PostMapping("/register")
    public String getRegister(@RequestParam("regisusername") String username,
            @RequestParam("regispassword") String password,
            @RequestParam("regisrepassword") String repassword) {
        if (!password.equals(repassword)) {
            return "redirect:/register";
        }
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password);
        usersRepository.save(newUser);
        return "redirect:/";
    }

    public boolean isLogin(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public void sendUser(Users user) {
        restTemplate.postForObject("http://localhost:8080/api/getusersession", user, String.class);
    }

    @GetMapping("/signout")
    public String signout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    //ดึงข้อมูลบนตาราง score มาดูของคนที่ล็อคอินอยู่ ทำการรวมผลคะแนนทั้งหมด
    @GetMapping("/viewscore")
    public String viewscore(HttpSession session, Model model) {
        if (isLogin(session)) {
            Users currentUser = (Users) session.getAttribute("user");
            double sumScore = 0.0;
            List<Scores> userScore = new ArrayList<>();
            Iterable<Scores> allScore = scoresRepository.findAll();
            for (var score : allScore) {
                if (score.getUser() != null && (score.getUser().getUserID() == currentUser.getUserID())) {
                    sumScore += score.getScore();
                    userScore.add(score);
                }
            }
            model.addAttribute("userScore", userScore);
            model.addAttribute("sumScore", sumScore);
            return "viewscore";
        }
        return "redirect:/login";
    }
}
