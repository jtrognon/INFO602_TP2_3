package blockchain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import hasher.StringHasher;

public class Block {
	private int index;
	private long date;
	private String author;

	private String prevHash;
	private String hash;
	private String sign;
	
	private String data;
	
	public Block(int index, String prevHash, String author, String sign, String hash, String data) {
		this.index = index;
		this.prevHash = prevHash;
		this.author = author;
		this.sign = sign;
		this.hash = hash;
		this.data = data;
		
		// get creation time
		this.date = System.currentTimeMillis();
		
	}
	
	public Block(int index, String prevHash, String author, String sign, String data) {
		this(
				index, 
				prevHash, 
				author,
				sign,
				"",
				data
			);
		
		this.hash = new StringHasher().calculateHash(index + prevHash + author + date + sign + data); // compute hash value 
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
	
	public String toJSON() {
		return "{ " +
				"index: " + index +
				", previousHash: " + prevHash +
				", author: " + author +
				", creationTime: " + date +
				", hash: " + hash +
				", signature: " + sign +
				", data: " + data +
				" }";
	}
	
	static public Block fromJSON(String json, Block prevBlock) {
		CandidateBlock cb= new CandidateBlock(prevBlock);
		
		json = json.replace('{', ' ');
		json = json.replace('}', ' ');
		String[] categories = json.split(",\s*");

		for (String c: categories) {			
			String[] cArray  = c.split(":");
			
			assert cArray.length == 2: "Block.fromJSON : You have too many ':' or not enough ':'.";
			
			String key = cArray[0].replaceAll("\s+", "");
			String value = cArray[1];
			
			if (key != "data") {
				value = value.replaceAll("\s+", "");
			}
			
			switch (key) {
			case "index": {
				try {
					cb.setIndex(Integer.parseInt(value));					
				} catch (NumberFormatException e) {
					System.err.println("The block id needs to be an integer");
					e.printStackTrace();
				}
				break;
			}
			case "previousHash":
				cb.setPrevHash(value);
				break;
			case "author":
				cb.setAuthor(value);
				break;
			case "creationTime":
				try {
					cb.setDate(Long.parseLong(value));					
				} catch (NumberFormatException e) {
					System.err.println("The block's creation date needs to be a long");
					e.printStackTrace();
				}
				break;
			case "hash":
				cb.setHash(value);
				break;
			case "signature":
				cb.setSign(value);
				break;
			case "data":
				cb.setData(value);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + key);
			}
		}
		return cb.createCorrespondingBlock();
	}
	
	public void save(String path){
		try (PrintWriter writer = new PrintWriter(path + "/bloc" + this.index + ".data", "UTF-8")){
			writer.println(this.index);
			writer.println(this.prevHash);
			writer.println(this.author);
			writer.println(this.date);
			writer.println(this.hash);
			writer.println(this.sign);
			writer.println(this.data);
		} catch (IOException e) {
			System.err.println("Error when saving block " + this.index);
			e.printStackTrace();
		}
	}
} 