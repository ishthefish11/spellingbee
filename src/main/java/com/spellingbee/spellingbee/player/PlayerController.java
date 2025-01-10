package com.spellingbee.spellingbee.player;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @GetMapping("/players/username/{username}")
    public ResponseEntity<Player> getPlayerByUsername(@PathVariable String username) {
        Player player  = playerService.getPlayerByUsername(username);
        return ResponseEntity.ok(player);
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Player player, HttpServletResponse response) {
        return playerService.login(player, response);
    }


}
