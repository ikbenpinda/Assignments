package huffmancodering;

import java.io.*;
import java.util.*;

/**
 * Processing class for Huffman encryption.
 * @author Etienne
 */
public class Huffman {

	private List<Node> tree;
	
	/**
	 * 
	 */
	public Huffman() {
		//
	}
	
	/**
	 * 
	 * @param text
	 */
	public void Encrypt(String text){				
		measureFrequencies(text);		
		Collections.sort(tree, new Node.frequencyComparator());
		//writeTree();
		//readTree();
	}
	
	/**
	 * 
	 * @param tree
	 */
	public void Decrypt(List<Node> tree){
		readTree(tree);
	}
	
	/**
	 * Measures the frequency of every character in the given text.
	 */
	private void measureFrequencies(String text){
		List<Character> characters = new ArrayList();
		Set<Character> charset = new HashSet();
		
		for (char c : text.toCharArray()) {
			charset.add(c);
			characters.add(c);
		}
		
		for (Character character : charset) {
			int count = Collections.frequency(characters, character);
			Node node = new Node(character, count);
			tree.add(node);
		}
	}

	/**
	 * Creates a new Huffman-tree.
	 */
	private void writeTree() {
		// Take 2 lowest as a leaves
		int index = 0;
		Node first = tree.get(index);
		
		Node second = tree.get(index);
		
		// Create node with sum of leaves' frequenties (Sheets, stap 3).
		
		
		// Repeat until one left				
		
	}

	/**
	 * Reads from a Huffman-tree.
	 */
	private String readTree(List<Node> tree) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public static void main(String[] args) throws Exception {
		Huffman f = new Huffman();
		f.Encrypt("topkek");
		System.out.println("done.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
	}	
}
