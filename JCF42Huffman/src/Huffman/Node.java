package Huffman;

/**
 * Created by Etienne on 05/03/15.
 * Represents an individual node(a leaf or a branch) within a Huffman tree.
 */
public class Node implements Comparable{

    public final Character character;
    public final int count;
    public Node left;
    public Node right;

    /**
     * Creates a leaf node with the given character and its count.
     * @param character the Unicode char.
     * @param count the frequency of this char in the initially given text.
     */
    public Node(char character, int count){
        this.character = character;
        this.count = count;
    }

    /**
     * Creates a branch node with the count set to the sum of both subnodes' count.
     * @param left the node with the lower count.
     * @param right the node with the higher count.
     */
    public Node(Node left, Node right){
        this.character = null;  // Remember to use Character, as char cannot be set to null.
        this.count = left.count + right.count;
        this.left = left;
        this.right = right;
    }

    /**
     * Indicates whether this node is a branch or a leaf.
     * @return true if this node is a leaf, false otherwise.
     */
    public boolean isLeaf(){
        return character != null;
    }

    /**
     * Sorts nodes by frequency from low to high.
     * @param o the node to compare with as an Object.
     * @return -1 if this.count > o.count, 0 if equal, 1 if this.count < o.count.
     */
    @Override
    public int compareTo(Object o) {
        Node n = (Node)o;
        return Integer.compare(count, n.count);
    }
}
