package blockchain;

import java.io.File;
import java.util.ArrayList;

public class Blockchain {
	private int id;
	private ArrayList<Block> blocks;
	
	public Blockchain(int id) {
		this.id = id;
		
		this.blocks = new ArrayList<Block>();
	}
	
	public void createAndAddBlockFromData(String data) {
		int size = this.blocks.size();
		
		if (size == 0) {
			this.blocks.add(new Block(0, "0", data));
		} else {
			this.blocks.add(new Block(size, this.blocks.getLast().getHash(), data));
		}
	}
	
	@Override
	public String toString() {
		String res = "";
		
		for (Block block : this.blocks) {
			res += block.toString();
		}
		
		return res;
	}
	
	
	public void save() {
		// Create the blockchain's folder
		String path = "./blockchain_" + this.id;
		new File(path).mkdirs();
		
		// Save blocks
		for (Block block : blocks) {
			block.save(path);
		}
	}
}
