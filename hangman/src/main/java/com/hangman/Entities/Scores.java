package com.hangman.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="score")
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scoreID;
    @Column(name="score")
    private double score;
    @Column(name="timestamp")
    private String timeStamp;
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
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
