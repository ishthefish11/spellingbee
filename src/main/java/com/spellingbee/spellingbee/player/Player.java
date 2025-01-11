package com.spellingbee.spellingbee.player;

import com.spellingbee.spellingbee.game.Game;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    private String playerName;
    private String password;
    private int highScore;
    @ElementCollection
    private List<Integer> lastTenScores;
    private int totalGamesPlayed;
    private long lastLoginTimestamp;
    private long accountCreationTimestamp;

    @OneToMany(mappedBy = "player")
    private List<Game> games;

    public Player() {

    }

    public Player(String playerName, String password) {
        this.playerName = playerName;
        this.password = password;
        this.highScore = 0;
        this.lastTenScores = new ArrayList<>();
        this.games = new ArrayList<>();
        this.totalGamesPlayed = 0;
        this.accountCreationTimestamp = System.currentTimeMillis();
        this.lastLoginTimestamp = System.currentTimeMillis();
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHighScore() {
        return highScore;
    }

    public List<Integer> getLastTenScores() {
        return lastTenScores;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public long getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public long getAccountCreationTimestamp() {
        return accountCreationTimestamp;
    }

    public List<Game> getGames() {
        return games;
    }

    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", password='" + password + '\'' +
                ", highScore=" + highScore +
                ", lastTenScores=" + lastTenScores +
                ", totalGamesPlayed=" + totalGamesPlayed +
                ", lastLoginTimestamp=" + lastLoginTimestamp +
                ", accountCreationTimestamp=" + accountCreationTimestamp +
                ", games=" + games +
                '}';
    }

    public void setHighScore(int newScore) {
        if (newScore > this.highScore) {
            this.highScore = newScore;
        }
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

    public void setLastLoginTimestamp() {
        this.lastLoginTimestamp = System.currentTimeMillis();
    }

}
