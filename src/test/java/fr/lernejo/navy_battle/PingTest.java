package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

class PingTest {
    @org.junit.jupiter.api.Test
    void simpleGetPing() throws IOException {
        String[] args = {"1234"};
        Launcher.main(args);

        URL url = new URL("http://localhost:1234/ping");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        Assertions.assertEquals(status, 200, "Le statut HTTP n'est pas valide");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        StringBuilder content = new StringBuilder();
        String ligne;
        while ((ligne = in.readLine()) != null) {
            content.append(ligne);
        }
        in.close();
        Assertions.assertEquals(content.toString(), "OK", "Le message n'est pas valide");
    }
}