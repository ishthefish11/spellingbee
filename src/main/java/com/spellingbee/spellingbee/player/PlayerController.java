package com.spellingbee.spellingbee.player;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        String token = playerService.verify(player); // Generate/verify JWT or session token

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Set the token as an HttpOnly cookie
        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Use this in production with HTTPS
        cookie.setPath("/"); // Make cookie available across the entire domain
        cookie.setMaxAge(60 * 60 * 24); // Set cookie expiry (1 day in this case)
        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }

}
