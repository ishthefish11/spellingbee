package game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import user.UserService;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserService userService;

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
            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());
                word = json.getString("word");
            } else {
                System.out.println("Error retrieving random word: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return word;
    }

    public Game startGame(Long userId) {
        Game game = new Game(getRandomWord(), userId); // Create a new game
        return gameRepository.save(game); // Save to the database
    }

    public void updateGame(Long id, int score) {
        Game game = gameRepository.findById(id).get();
        game.setScore(score);
        gameRepository.save(game);
    }

    public boolean checkWord(Long id, String word) {
        Game game = gameRepository.findById(id).get();
        return game.getWord().equals(word);
    }
}
