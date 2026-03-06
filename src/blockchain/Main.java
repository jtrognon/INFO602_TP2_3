package blockchain;

public class Main {
	public static void main(String[] args) {
		Blockchain bc = new Blockchain(1);
		bc.createAndAddBlockFromData("Bonjour - Léna");
		bc.createAndAddBlockFromData("Salut - Antonin");
		bc.createAndAddBlockFromData("Vous faîtes quoi ? - Coralie");
		bc.createAndAddBlockFromData("Rien - Julie");
		
		System.out.println(bc);
	}
}
