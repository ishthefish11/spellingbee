package com.spellingbee.spellingbee.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private int highScore;
    private List<String> wordsLostTo;
    private List<Integer> lastTenScores;
    private int totalGamesPlayed;
    private long lastLoginTimestamp;
    private long accountCreationTimestamp;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.highScore = 0;
        this.wordsLostTo = new ArrayList<>();
        this.lastTenScores = new ArrayList<>();
        this.totalGamesPlayed = 0;
        this.accountCreationTimestamp = System.currentTimeMillis();
        this.lastLoginTimestamp = System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void updateHighScore(int newScore) {
        if (newScore > this.highScore) {
            this.highScore = newScore;
        }
    }

    public void addWordLostTo(String word) {
        this.wordsLostTo.add(word);
    }

    public void addNewScore(int score) {
        if (this.lastTenScores.size() >= 10) {
            this.lastTenScores.removeFirst();
        }
        this.lastTenScores.add(score);
    }

    public void incrementGamesPlayed() {
        this.totalGamesPlayed++;
    }

    public void updateLastLoginTimestamp() {
        this.lastLoginTimestamp = System.currentTimeMillis();
    }
}
