package com.spellingbee.spellingbee.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/play")
    public Long startGame(@RequestParam Long playerId) {
        Game game = gameService.startGame(playerId);
        return game.getGameId();
    }

    @GetMapping("/play/{id}/check-word/{word}")
    public boolean checkWord(@PathVariable Long id, @PathVariable String word) {
        return gameService.checkWord(id, word);
    }

    @GetMapping("/play/{id}/definition")
    public String getWordDefinition(@PathVariable Long id) {
        return gameService.getWordDefinition(id);
    }

    @GetMapping("/play/{id}")
    public Game advanceInGame(@PathVariable Long id) {
        return gameService.advanceInGame(id);
    }
}
