package ru.geekbrains.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 1400;
    private Map<String, ClientHandler> clients = new HashMap<>();

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected");

            // Создаем обработчик для нового клиента
            ClientHandler handler = new ClientHandler(this, clientSocket);
            Thread t = new Thread(handler);
            t.start();
        }
    }

    synchronized void addClient(String username, ClientHandler handler) {
        clients.put(username, handler);
    }

    synchronized void removeClient(String username) {
        clients.remove(username);
    }

    synchronized void broadcastMessage(String fromUser, String toUser, String message) {
        ClientHandler recipient = clients.get(toUser);
        if (recipient != null) {
            recipient.sendMessage(fromUser + ": " + message);
        }
    }
}
