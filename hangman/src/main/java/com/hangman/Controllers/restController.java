package com.hangman.Controllers;

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

    @PostMapping("/getusersession")
    public ResponseEntity<String> getUserSession(@RequestBody Users user, HttpSession session) {
        // Store the current user in the session
        session.setAttribute("currentUser", user);
        return ResponseEntity.ok("User session received successfully");
    }

    @PostMapping("/savescore")
    public ResponseEntity<String> saveScore(@RequestBody double score, HttpSession session) {
        try {
            // Retrieve the current user from the session using the correct key
            Users currentUser = (Users) session.getAttribute("user");

            // Check if a user is logged in
            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
            }
            // Save the score for the current user
            Scores scores = new Scores();
            scores.setScore(score);
            scores.setUser(currentUser);
            scoresRepository.save(scores);

            return ResponseEntity.ok("Score has been saved.");
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save score.");
        }
    }

}
