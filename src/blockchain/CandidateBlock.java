package blockchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import hasher.StringHasher;

public class CandidateBlock {
	private int index;
	private long date;

	private StringHasher hasher;
	private String prevHash;
	private String hash;
	
	private String data;
	
	private Block prevBlock;
	
	public CandidateBlock(Block prevBlock) {
		this.prevBlock = prevBlock;
		
		this.hasher = new StringHasher();
	}
	
	public void loadFromFile(File file) {
		Scanner sc;
		try {
			sc = new Scanner(file);
		
			int lineNumber = 1;
			this.data = "";
			
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				
				switch (lineNumber) {
				case 1: {
					try {
						this.index = Integer.parseInt(line);
					} catch (NumberFormatException e) {
						System.err.println("CandidateBlock.loadFromFile: block index ("+line+") isn't a number.");
						e.printStackTrace();
					}
					break;
				}
				case 2: {
					this.prevHash = line;
					break;
				}
				case 3: {
					this.hash = line;
					break;
				}
				default:
					this.data += line;
				}
				
				lineNumber++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isValid() {
		return 	( // first block or the previous block's given data corresponds to the previous block
					(
						this.index == 0 && 
						this.prevHash != "" && 
						this.prevBlock == null 
					) || 
					(
						this.prevBlock != null &&
						this.prevHash == this.prevBlock.getHash() &&
						this.index == this.prevBlock.getIndex()
					)	
				) && // Hash's value corresponds to the given hash
				this.data != null &&
				this.hash == this.hasher.calculateHash(this.index + this.prevHash + this.data);
	}
	
	public Block createCorrespondingBlock() {
		assert this.isValid() : "CandidateBlock.createcorrespondingBlock : Cannot create a block from an invalid candidate.";
		return new Block(this.index, this.prevHash, this.hash, this.data);
	}
}
