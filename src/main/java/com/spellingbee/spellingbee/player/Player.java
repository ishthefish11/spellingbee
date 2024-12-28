package com.spellingbee.spellingbee.player;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"Player\"") // Escapes the table name
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    private String playername;
    private String email;
    private int highScore;
    private List<String> wordsLostTo;
    private List<Integer> lastTenScores;
    private int totalGamesPlayed;
    private long lastLoginTimestamp;
    private long accountCreationTimestamp;

    public Player(String playername, String email) {
        this.playername = playername;
        this.email = email;
        this.highScore = 0;
        this.wordsLostTo = new ArrayList<>();
        this.lastTenScores = new ArrayList<>();
        this.totalGamesPlayed = 0;
        this.accountCreationTimestamp = System.currentTimeMillis();
        this.lastLoginTimestamp = System.currentTimeMillis();
    }

    public String getPlayerName() {
        return playername;
    }

    public void setPlayerName(String playerName) {
        this.playername = playername;
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
