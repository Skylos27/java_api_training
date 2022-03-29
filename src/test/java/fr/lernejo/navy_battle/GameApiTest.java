package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.utilities.ServerParser;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class GameApiTest {
    @org.junit.jupiter.api.Test
    void simpleGetApi() throws IOException {
        String[] args = {"1234"};
        Launcher.main(args);

        URL url = new URL("http://localhost:1234/api/game/start");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        Assertions.assertEquals(status, 202, "Le statut HTTP n'est pas valide");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        StringBuilder content = new StringBuilder();
        String ligne;
        while ((ligne = in.readLine()) != null) {
            content.append(ligne);
        }
        in.close();
        JSONObject json = new JSONObject(content.toString());
        ServerParser parsed = ServerParser.fromJSON(json);
        String message = parsed.getMessage();
        String urlr = parsed.getUrl();
        String id = parsed.getId();
        Assertions.assertEquals(message, "Server started", "Le message n'est pas valide");
        Assertions.assertEquals(urlr, "http://localhost:1234", "L'url n'est pas le bon'");
        Assertions.assertTrue(id.contains("-"));
    }

    @org.junit.jupiter.api.Test
    void GoodRequestTest() throws IOException, InterruptedException {
        String[] args = {"1256"};
        Launcher.main(args);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1256/api/game/start"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(),202);

    }
    @org.junit.jupiter.api.Test
    void BadRequestTest() throws IOException, InterruptedException {
        String[] args = {"1257"};
        Launcher.main(args);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1257/api/start"))
                .setHeader("Accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(),404);
    }

    @org.junit.jupiter.api.Test
    void startFails() {
        Assertions.assertThrows(
                NullPointerException.class,
                () -> Launcher.main(null)
        );
    }
    @org.junit.jupiter.api.Test
    void startFails2() {
        String[] test = new String[0];
        Assertions.assertThrows(
                NullPointerException.class,
                () -> Launcher.main(test), "Usage: Launcher [port]"
        );
    }
}