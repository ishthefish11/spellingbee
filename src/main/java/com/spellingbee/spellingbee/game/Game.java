package com.spellingbee.spellingbee.game;

import com.spellingbee.spellingbee.player.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long GAME_SESSION_ID;
    private String word;
    private Player player;
    private int score;
    private boolean active;

    public Game() {

    }

    public Game(String word, Long playerId) {
        score = 0;
        this.word = word;
        active = true;
    }

    public long getGameId() {
        return GAME_SESSION_ID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void endGame() {
        active = false;
    }
}
