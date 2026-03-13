package blockchain;

public class Main {
	public static void main(String[] args) {
//		BlockchainServer bc = new BlockchainServer(12345);
//		
//		bc.load();
//		bc.run();
		
		
		
		
//		bc.createAndAddBlockFromData("Bonjour - Léna");
//		bc.createAndAddBlockFromData("Salut - Antonin");
//		bc.createAndAddBlockFromData("Vous faîtes quoi ? - Coralie");
//		bc.createAndAddBlockFromData("Rien - Julie");
//		
//		bc.save();

		
		
//		Server server = new Server(12345);
//		server.run();
		
		BlockchainClient client = new BlockchainClient("localhost", 12345);
		client.getBlockChain();
		
		System.out.println(client);
	}
}
