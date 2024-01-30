package com.hangman.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hangman.Entities.Scores;
import com.hangman.Entities.Users;
import com.hangman.Repositories.ScoresRepository;

@RestController
@RequestMapping("/api")
public class restController {
    private Users currentUser;

    @Autowired
    ScoresRepository scoresRepository;
    @PostMapping("/getusersession")
    public ResponseEntity<String> getUserSession(@RequestBody Users user){
        currentUser = user;
        return ResponseEntity.ok("get user");
    } 

    @PostMapping("/savescore")
    public ResponseEntity<String> saveScore(@RequestBody double score){
        Scores scores = new Scores();
        scores.setScore(score);
        scores.setUser(currentUser);
        scoresRepository.save(scores);
        return ResponseEntity.ok("Score has been saved.");
    }
}
