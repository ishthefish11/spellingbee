package com.spellingbee.spellingbee.game;

import com.spellingbee.spellingbee.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import com.spellingbee.spellingbee.player.PlayerService;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerService playerService;

    public String getRandomWord() {
        String url = "https://api.api-ninjas.com/v1/randomword";
        String apiKey = "5wq6WZNeIMtDmNoFMuLzew==ZVRUZqbiObFiNp7s";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Api-Key", apiKey)
                .GET()
                .build();

        String word = null;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            word = json.getJSONArray("word").getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return word;
    }

    public String getWordDefinition(Long id) {
        String word = gameRepository.findById(id).get().getWord();
        String firstDefinition = null;
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            firstDefinition = json.getJSONArray("meanings").getJSONObject(0).getJSONArray("definitions").getJSONObject(0).getString("definition");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return firstDefinition;
    }

    public Game startGame(Long userId) {
        Game game = new Game(getRandomWord(), userId);
        return gameRepository.save(game);
    }

    public Game advanceInGame(Long id) {
        Game game = gameRepository.findById(id).get();
        game.setWord(getRandomWord());
        game.setScore(game.getScore() + 1);
        return gameRepository.save(game);
    }

    public boolean checkWord(Long id, String word) {
        boolean flag = true;
        Game game = gameRepository.findById(id).get();
        if (!game.getWord().equals(word)) {
            flag = false;
            game.endGame();
            Player player = game.getUser();
            player.addNewScore(game.getScore());
            player.incrementGamesPlayed();
            player.addWordLostTo(game.getWord());
            player.updateHighScore(game.getScore());
        }
        return flag;
    }
}
