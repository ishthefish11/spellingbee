package com.spellingbee.spellingbee.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        return playerRepository.findById(id).get();
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
