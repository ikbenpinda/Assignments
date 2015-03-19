package Huffman;

import java.util.*;

/**
 * Created by Etienne on 05/03/15.
 * Huffman encryption following this given algorithm:
 * https://en.wikipedia.org/wiki/Huffman_coding#Compression
 */
public class Huffman {

    private PriorityQueue<Node> queue;

    /**
     * Track all prefixes per character for encoding.
     */
    private Map<Character, String> prefixes;

    /**
     * Since we need the tree to both encode and decode, make it global.
     */
    private Node tree;

    /**
     * Creates a new Huffman encryption object.
     */
    public Huffman(){
        queue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.compareTo(n2);
            }
        });
        prefixes = new HashMap();
    }

    /**
     * Encrypts any given text to a Huffman tree.
     * @param text a Unicode string with the text to encrypt.
     */
    public String encrypt(String text){
        // Measure frequency of every character in the text.
        measureFrequencies(text);

        // Sort by frequency.
        // - Queues are auto-sorted.

        // Create a tree from all the nodes.
        tree = createTree();

        // Print a list of all characters, their frequencies, and prefixes.
        System.out.println("\n" + "Character" + "\t " + "Count" + "\t " + "Prefix");
        printTree(tree, "");

        // Return the encoded text.
        String zeroesandones = text;

        // Replace every character with its prefix.
        for (Map.Entry entry : prefixes.entrySet()) {
            String character = entry.getKey().toString();
            String prefix = entry.getValue().toString();

            zeroesandones = zeroesandones.replace(character, prefix);
            //System.out.println(zeroesandones); // Uncomment this line to see every step.
        }

        return zeroesandones;
    }

    /**
     * Decrypts any given tree to a human-readable string of text.
     * @param code a string of zeroes and ones that needs decryption.
     */
    public String decrypt(String code){
        String result = ""; // This stores the completely decrypted string.
        Node node = tree;   // Start from the tree.

        // There is literally no reason to use recursion here.
        // Unless you like StackOverflowExceptions?
        for (int i = 0; i < code.length(); i++) {

        // A little control over the looping is needed, so no foreach-loops. :(
        Character c = code.toCharArray()[i];

            if (node.isLeaf()) {
                result += node.character;
                // End of route; reset.
                node = tree;
                // Since this happens, already on the next char: take a step back.
                i = i -1;
            }
            else {
                switch (c) {
                    case '0':
                        node = node.left;
                        break;
                    case '1':
                        node = node.right;
                        break;
                    default:
                        break;
                }
            }
        }

        // This loop leaves the last character unappended.
        result += node.character;
        return result;
    }

    /**
     * Prints the tree by printing every character with its frequency and prefix.
     * @param node the tree node.
     * @param prefix Must be empty, this is used for recursion.
     * @return a String containing one record.
     */
    private String printTree(Node node, String prefix){

        if (node.isLeaf()) {
            // Print the character, its count, and its prefix.
            prefixes.put(node.character, prefix);
            System.out.println(node.character + "\t \t \t " + node.count + "\t \t " + prefix);

        } else {

            // Go left.
            prefix += '0';
            printTree(node.left, prefix);
            prefix = prefix.substring(0, prefix.length() - 1);

            // Go right.
            prefix += '1';
            printTree(node.right, prefix);
            prefix = prefix.substring(0, prefix.length() - 1);

        }

        return prefix;
    }

    /**
     * Measures the frequency of every character in the string and
     * creates a new node containing the data, and adds it to a list of nodes.
     * @param text a Unicode string with the text to encrypt.
     */
    private void measureFrequencies(String text){
        char[] chars = text.toCharArray();

        Set<Character> charset = new HashSet<Character>();
        List<Character> characters = new ArrayList();

        // Create a set of characters used.
        for (int i = 0; i < chars.length; i++) {
            characters.add(chars[i]);
            charset.add(chars[i]);
        }

        // Measure the frequency of each character.
        for (char character : charset) {
            int count = Collections.frequency(characters, character);
            Node node = new Node(character, count);
            queue.add(node);
        }
    }

    /**
     * Creates a tree from a list of nodes.
     * @return Node a recursive node representing the tree.
     */
    private Node createTree(){
        while (queue.size() > 1){

            // Remove the two nodes with lowest frequency from the queue.
            // Queue.poll(), unlike .Remove(), returns null instead of throwing an exception.
            Node left  = queue.poll();
            Node right = queue.poll();

            // Create a new branch node with these two nodes as children,
            // With a count equal to the sum of the two nodes' count.
            Node branch = new Node(left, right);

            // Add the new node to the queue.
            queue.add(branch);
        }

        // The remaining node is the root node and the tree is complete.
        return queue.poll();
    }
}
