package com.spellingbee.spellingbee.player;

import com.spellingbee.spellingbee.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;

    public ResponseEntity<Player> createPlayer(Player player) {
        Optional<Player> findPlayer = playerRepository.findByPlayerName(player.getPlayerName());
        if (findPlayer.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(playerRepository.save(new Player(player.getPlayerName(), passwordEncoder.encode(player.getPassword()))), HttpStatus.CREATED);
    }

    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    public ResponseEntity<Player> getPlayer(Long id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(player.get(), HttpStatus.OK);
    }


    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public void updateLastLoginTimestamp(Long id) {
        playerRepository.findById(id).get().setLastLoginTimestamp();
    }

    public List<Player> getAllPlayers() {
        List<Player> arr = new ArrayList<>();
        arr.addAll(playerRepository.findAll());
        return arr;
    }

    public String verify(Player player) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(player.getPlayerName(), player.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(player.getPlayerName());
        } else {
            return "fail";
        }
    }

    public Player getPlayerByUsername(String playerName) {
        return (playerRepository.findByPlayerName(playerName).isPresent()) ? playerRepository.findByPlayerName(playerName).get() : null;
    }

    public ResponseEntity<?> login(Player player, HttpServletResponse response) {
        String token = verify(player);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Long playerId = getPlayerByUsername(player.getPlayerName()).getPlayerId();

        // add jwt token as a cookie
        Cookie authTokenCookie = new Cookie("authToken", token);
        authTokenCookie.setHttpOnly(true);
        authTokenCookie.setSecure(false);
        authTokenCookie.setPath("/");
        authTokenCookie.setMaxAge(60 * 60 * 24); // 1 day
        response.addCookie(authTokenCookie);

        // add playerId as a cookie (eliminates need for future requests requiring an id)
        Cookie playerIdCookie = new Cookie("playerId", String.valueOf(playerId));
        playerIdCookie.setHttpOnly(false);
        playerIdCookie.setSecure(false);
        playerIdCookie.setPath("/");
        playerIdCookie.setMaxAge(60 * 60 * 24); // 1 day
        response.addCookie(playerIdCookie);

        return ResponseEntity.ok("Login successful");
    }

}