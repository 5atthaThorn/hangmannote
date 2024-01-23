package com.hangman.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="score")
public class Scores {
    @Id
    private int scoreID;
    @Column(name="score")
    private double score;
    @ManyToOne
    @JoinColumn(name="userID")
    private Users user;
    public int getScoreID() {
        return scoreID;
    }
    public void setScoreID(int scoreID) {
        this.scoreID = scoreID;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }
    
}
