package com.spellingbee.spellingbee.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public ResponseEntity<Player> createPlayer(Player player) {
        Optional<Player> findPlayer = playerRepository.findByPlayerName(player.getPlayerName());
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

    public List<Player> getAllPlayers() {
        List<Player> arr = new ArrayList<>();
        arr.addAll(playerRepository.findAll());
        return arr;
    }
}