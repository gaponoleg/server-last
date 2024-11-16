package ru.geekbrains.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.UnknownHostException;

public class Program {

    public static void main(String[] args) {
        try
        {
            Server server = new Server();
            server.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
