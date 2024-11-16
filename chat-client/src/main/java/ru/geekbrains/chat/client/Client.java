package ru.geekbrains.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 1400;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public void connect() throws IOException {
        Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Enter your username:");
        username = reader.readLine();
        writer.println(username); // Отправка имени пользователя на сервер

        new Thread(() -> {
            try {
                BufferedReader serverReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                String message;
                while ((message = serverReader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length >= 3 && parts[0].equals("/msg")) {
                String toUser = parts[1];
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < parts.length; i++) {
                    sb.append(parts[i]).append(" ");
                }
                String msg = sb.toString().trim();
                writer.println(username + ":" + toUser + ":" + msg);
            }
        }
    }
}
