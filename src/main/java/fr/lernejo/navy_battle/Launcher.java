package fr.lernejo.navy_battle;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.utilities.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Launcher {

    private ServerParser localServer;
    private ServerParser remoteServer;

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Usage: Launcher [port]");
                throw new NullPointerException("Usage: Launcher [port]");
            }

            int serverPort = Integer.parseInt(args[0]);
            System.out.println("Listen port "  + serverPort);

            new Launcher().startServer(serverPort, args.length > 1 ? args[1] : null);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startServer(int port, String connectURL) throws IOException, InterruptedException {
        localServer = new ServerParser(
                UUID.randomUUID().toString(),
                "http://localhost:" + port,
                "Server started"
        );
        HttpServer new_server = HttpServer.create(new InetSocketAddress(port), 0);
        new_server.setExecutor(Executors.newSingleThreadExecutor());
        new_server.createContext("/ping", this::handlePing);
        new_server.createContext("/api/game/start", s -> startGame(new RequestHandler(s)));
        new_server.start();
        Client client = new Client();
        client.startClient(port, connectURL);
    }

    private void handlePing(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }

    public void startGame(RequestHandler handler) throws IOException {
        try {
            if (remoteServer != null) {
                handler.sendString(400, "Server full");
                return;
            }
            System.out.println(handler.getJSONObject());
            //remoteServer = ServerParser.fromJSON(handler.getJSONObject());

            handler.sendJSON(202, localServer.toJSON());

        } catch (Exception e) {
            e.printStackTrace();
            handler.sendString(400, e.getMessage());
        }
    }
}