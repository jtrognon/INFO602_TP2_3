package blockchain;

import java.io.File;
import java.util.ArrayList;

public class Blockchain {
	protected int id;
	protected ArrayList<Block> blocks;
	
	public Blockchain(int id) {
		this.id = id;
		
		this.blocks = new ArrayList<Block>();
	}
	
	public void createAndAddBlockFromData(String data) {
		int size = this.blocks.size();
		
		if (size == 0) {
			this.blocks.add(new Block(0, "0", "JT", "0", data));
		} else {
			this.blocks.add(new Block(size, this.blocks.getLast().getHash(), "JT", "0", data));
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
	
	public void load() {
		File directory = new File("./blockchain_" + this.id);
		File[] files = directory.listFiles();
		
		if (files != null && files.length > 0) {
			CandidateBlock cb = new CandidateBlock(null);
			cb.loadFromFile(files[0]);
			
			Block prevB = cb.createCorrespondingBlock();
			this.blocks.add(prevB);
			
			for (int i=1; i < files.length; i++) {
				cb = new CandidateBlock(prevB);
				cb.loadFromFile(files[i]);
				
				prevB = cb.createCorrespondingBlock();
				this.blocks.add(prevB);
			}
		}
	}
}
