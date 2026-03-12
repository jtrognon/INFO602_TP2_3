package blockchain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import hasher.StringHasher;

public class Block {
	private int index;
	private long date;

	private String prevHash;
	private String hash;
	
	private String data;
	
	public Block(int index, String prevHash, String hash, String data) {
		this.index = index;
		this.prevHash = prevHash;
		this.hash = hash;
		this.data = data;
		
		// get creation time
		this.date = System.currentTimeMillis();
		
	}
	
	public Block(int index, String prevHash, String data) {
		this(
				index, 
				prevHash, 
				new StringHasher().calculateHash(index + prevHash + data), // compute hash value 
				data
			);
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	@Override
	public String toString() {
		return "___________________________________________________\n\n" +
						
				// Block
				"- Block (" + this.index + ") " + this.hash + "\n\n" +
				
				// Creation date
				"- created on " + new Date(this.date) + "\n\n" +
				
				// Previous hash
				"- previous hash : " + this.prevHash + "\n\n" +
				
				// Data
				"----\n" +
				"Data\n" +
				this.data + "\n\n";
	}
	
	public void save(String path){
		try (PrintWriter writer = new PrintWriter(path + "/bloc" + this.index + ".data", "UTF-8")){
			writer.println(this.index);
			writer.println(this.prevHash);
			writer.println(this.hash);
			writer.println(this.data);
		} catch (IOException e) {
			System.err.println("Error when saving block " + this.index);
			e.printStackTrace();
		}
	}
} 