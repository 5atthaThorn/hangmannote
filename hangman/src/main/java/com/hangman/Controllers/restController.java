package com.hangman.Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hangman.Entities.Scores;
import com.hangman.Entities.Users;
import com.hangman.Repositories.ScoresRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class restController {

    @Autowired
    private ScoresRepository scoresRepository;

    //log in ผ่าน ขอ user id ได้มั้ย
    @PostMapping("/getusersession")
    public ResponseEntity<String> getUserSession(@RequestBody Users user, HttpSession session) {
        // Store the current user in the session
        session.setAttribute("currentUser", user);
        return ResponseEntity.ok("User session received successfully");
    }

    //save คะแนนที่ได้ลงฐานข้อมูล เวลาปัจจุบันในตอน save 
    @PostMapping("/savescore")
    public ResponseEntity<String> saveScore(@RequestBody Map<String, Object> scoreData, HttpSession session) {
        try {
            // Retrieve the current user from the session using the correct key
            double score = ((Number) scoreData.get("score")).doubleValue();
            String gameName = (String) scoreData.get("gameName");
            Users currentUser = (Users) session.getAttribute("user");

            // Check if a user is logged in
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
            }
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timeStamp = now.format(formatter);
            Scores scores = new Scores();
            scores.setScore(score);
            scores.setTimeStamp(timeStamp);
            scores.setUser(currentUser);
            scores.setGameName(gameName);
            scoresRepository.save(scores);

            return ResponseEntity.ok("Score has been saved.");
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save score.");
        }
    }

}
