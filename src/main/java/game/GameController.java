package game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/play")
    public String startGame() {
        String r = gameService.getRandomWord();
        System.out.println(r);
        return r;
    }

    @GetMapping("/play/{id}/check-word/{word}")
    public boolean checkWord(@PathVariable Long id, @PathVariable String word) {
        return gameService.checkWord(id, word);
    }

}
