package blockchain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.ClientHandler;

public class BlockchainServer extends Blockchain{
	private int port;
	
	public BlockchainServer(int port) {
		super(1);
		
		this.port = port;
	}
	
	private String toJSON() {
		// Create the blockchain's folder
		String res = "{\n";
		
		// Save blocks
		for (Block block : blocks) {
			res += block.toJSON() + "\n";
		}
		
		return res + "}";
	}
	
	
	public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur lancé sur le port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, this.toJSON()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




