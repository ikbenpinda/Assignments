package huffmancodering;

import java.util.*;
import org.omg.CORBA.*;

/**
 *
 * @author Etienne
 */
class Node implements Comparable<Node>{ // Internal

	// Encapsulation is overrated.
	public char c;
	public int frequency;
	public Node left;
	public Node right;	
	
	// TODO Leaf/Node class?
	public Node(char character, int frequency) {
		this.c = character;
		this.frequency = frequency;
	}
	
	public Node(Node left, Node right) {
		this.left = left;
		this.right = right;		
	}
	
	@Override
	public int compareTo(Node o) {
		return Integer.compare(o.frequency, frequency);
	}

	public static class frequencyComparator implements Comparator<Node>{

		public frequencyComparator() {
		}		
		
		@Override
		public int compare(Node o1, Node o2) {
				Node n1 = (Node) o1;
				Node n2 = (Node) o2;
				return n1.compareTo(n2);
		}
	}
}
