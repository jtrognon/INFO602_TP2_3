package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private String json;

    public ClientHandler(Socket socket, String json) {
        this.socket = socket;
        this.json = json;
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request = in.readLine();
            if ("EXEMPLE_INITIAL".equals(request)) {
                out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(12345);
        server.run();
    }
}
