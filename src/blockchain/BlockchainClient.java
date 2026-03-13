package blockchain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockchainClient extends Blockchain{
	private String serverAddress;
    private int serverPort;

    public BlockchainClient(String address, int port) {
    	super(1);
    	
        this.serverAddress = address;
        this.serverPort = port;
    }
    
    private String reverseJson(String json) {
    	String res = "";
    	
    	for (char c : json.toCharArray()) {
			res = c + res;
		}
    	
    	return res;
    }
    
    private void fromJson(String json) {
    	json = reverseJson(reverseJson(json.replace('{', ' ')).replace('}', ' '));
 
    	String[] lines = json.split("\n");
    	
    	if (lines.length > 0) {    		
			Block b = Block.fromJSON(lines[0], null);
			Block prevB = b;
			
			this.blocks.add(prevB);
			
			for (int i=1; i < lines.length; i++) {
				if (lines[i].length() > 5) {
					b = Block.fromJSON(lines[i], prevB);
					
					prevB = b;
					this.blocks.add(prevB);
				}
			}
    	} 
    }
    
    

    public void getBlockChain() {
        try (Socket socket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("EXEMPLE_INITIAL");
            String line = in.readLine();
            String json = line;
            while (line != null) {
                line = in.readLine();
                json += line + "\n";
            }
            
            this.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
