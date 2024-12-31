package com.spellingbee.spellingbee.game;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    Game findByPlayer_PlayerIdAndActiveTrue(Long playerId);
}