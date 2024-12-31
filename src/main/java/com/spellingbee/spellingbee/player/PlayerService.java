package com.spellingbee.spellingbee.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void createPlayer(Player player) {
        Optional<Player> existingPlayer = playerRepository.findByPlayername(player.getPlayerName());
        if (existingPlayer.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        playerRepository.save(player);
    }

    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}