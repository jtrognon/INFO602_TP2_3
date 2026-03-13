package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;

    public Client(String address, int port) {
        this.serverAddress = address;
        this.serverPort = port;
    }

    public void getBlockChain() {
        try (Socket socket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("EXEMPLE_INITIAL");
            String line = in.readLine();
            while (line != null) {
                System.out.println(line);
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        Client client = new Client("localhost", 12345);
        client.getBlockChain();
    }
}
