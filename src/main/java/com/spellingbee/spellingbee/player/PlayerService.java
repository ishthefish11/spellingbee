package com.spellingbee.spellingbee.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public ResponseEntity<Player> createPlayer(Player player) {
        System.out.println("Alright, did it make it THIS far, though?");
        Optional<Player> findPlayer = playerRepository.findByPlayername(player.getPlayerName());
        if (findPlayer.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(playerRepository.save(player), HttpStatus.CREATED);
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
        playerRepository.findById(id).get().updateLastLoginTimestamp();
    }
}