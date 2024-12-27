package game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import user.User;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long GAME_SESSION_ID;
    private String word;
    private User user;
    private int score;
    private boolean active;

    public Game(String word, Long userId) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
