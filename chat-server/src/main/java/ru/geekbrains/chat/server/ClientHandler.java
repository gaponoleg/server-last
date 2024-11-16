package ru.geekbrains.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private Server server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String username;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Читаем имя пользователя
            username = input.readLine();
            server.addClient(username, this);
            System.out.println("New user connected: " + username);

            String message;
            while ((message = input.readLine()) != null) {
                String[] parts = message.split(":");
                if (parts.length == 3) {
                    String fromUser = parts[0];
                    String toUser = parts[1];
                    String msg = parts[2];
                    server.broadcastMessage(fromUser, toUser, msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(username);
            System.out.println("User disconnected: " + username);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }
}
