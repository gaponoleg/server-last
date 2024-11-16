package ru.geekbrains.chat.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connect();
    }

}
