package com.spellingbee.spellingbee.player;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/players")
    public void createPlayer(@RequestBody Player player) {
        System.out.println("Did we make it this far?");
        System.out.println("Player received: " + player);
        playerService.createPlayer(player);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PutMapping("/players")
    public void updatePlayer(@RequestBody Player player) {
        playerService.updatePlayer(player);
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

    @PutMapping("/players/{id}/logged-in")
    public void updateLoggedInTimestamp(@PathVariable Long id) {
        playerService.updateLastLoginTimestamp(id);
    }
}
