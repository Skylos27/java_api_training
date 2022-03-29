package fr.lernejo.navy_battle.utilities;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

public class Client {
    public void startClient(int port, String connectURL) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .build();
        if(connectURL != null) {
            HttpRequest requetePost = HttpRequest.newBuilder()
                    .uri(URI.create(connectURL + "/api/game/start"))
                    .setHeader("Accept", "application/json")
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"hello\"}"))
                    .build();
            client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        }
    }
}
