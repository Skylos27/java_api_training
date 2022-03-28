package fr.lernejo.navy_battle;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Launcher {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.err.println("Usage: Launcher [port]");
                throw new NullPointerException("Usage: Launcher [port]");
            }

            int port = Integer.parseInt(args[0]);
            System.out.println("Début de l'écoute de  http://localhost:" + port + "/");

            startServer(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startServer(int port) throws IOException {
        HttpServer new_server = HttpServer.create(new InetSocketAddress(port), 0);
        new_server.createContext("/ping", Launcher::handlePing);
        new_server.start();
    }

    private static void handlePing(HttpExchange exchange) throws IOException {
        String body = "Hello";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
}