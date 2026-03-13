package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur lancé sur le port " + port);

            while (true) {
            	System.out.println("toto");
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, "toto").start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

