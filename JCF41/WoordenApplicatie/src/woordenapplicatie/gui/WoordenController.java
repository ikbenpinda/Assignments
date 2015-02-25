/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woordenapplicatie.gui;

import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
	// For the assignment we need the first verse only. 
	// I don't know why they did this.
   private static final String DEFAULT_TEXT
		= "Een, twee, drie, vier\n"
		+ "Hoedje van, hoedje van\n"
		+ "Een, twee, drie, vier\n"
		+ "Hoedje van papier\n" /*+
		 "\n" +
		 "Heb je dan geen hoedje meer\n" +
		 "Maak er één van bordpapier\n" +
		 "Eén, twee, drie, vier\n" +
		 "Hoedje van papier\n" +
		 "\n" +
		 "Een, twee, drie, vier\n" +
		 "Hoedje van, hoedje van\n" +
		 "Een, twee, drie, vier\n" +
		 "Hoedje van papier\n" +
		 "\n" +
		 "En als het hoedje dan niet past\n" +
		 "Zetten we 't in de glazenkas\n" +
		 "Een, twee, drie, vier\n" +
		 "Hoedje van papier"*/;

   /**
    * Global list of unique words so we dont have to calculate every time.
    */
    private HashSet<String> uniquewords;
    
    /**
     * Global list of all words, including case sensitive doubles.
     */
    private ArrayList<String> allwords = new ArrayList<String>();
   
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
	uniquewords = new HashSet<>();
	allwords = new ArrayList<>();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Calculate amount.">
    
    /**
     * Counts the amount of words in total and the amount of exactly unique ones.
     * @param event 
     */
    @FXML
    private void aantalAction(ActionEvent event) {
	taOutput.clear();
	uniquewords = new HashSet<String>();
	allwords = new ArrayList<String>();
	
	// This is the regex we're using. 
	// It matches groups of Unicode characters categorized as Letter.
	String pattern = "\\P{L}+";
	    
	// Gets the text and splits it on the regex pattern.
	String[] output = taInput.getText().split(pattern);
	    
	// Add all the elements from the array to the list because collections.	
	ArrayList<String> list = new ArrayList();
	for (String s : output) {
		// Lowercase it because sets are case sensitive.
		list.add(s.toLowerCase());
	}
	    
	// Sets don't allow for doubles so if we add the full list to the set,
	// we get a doubles-filtered list.
	HashSet<String> set = new HashSet();
	set.addAll(list);
	    
	String amount = "Totaal aantal woorden: " + output.length;
	String uamount = "Aantal verschillende woorden: " + set.size();
	    
	// For debugging uses, prints all the resulted words.
	for (String s : set) {
		System.out.println(s);
	}
	
	// Adds all the words to global lists for the other functions to use.
	// Saves calculation time for other functions.
	for (String s : list) {
	    allwords.add(s);
	}
	for (String s : set) {
		uniquewords.add(s);
	}
	
	    
	taOutput.appendText(amount + "\n" + uamount);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sort.">
    
    /**
     * Sorts the unique words in reverse alphabetical order.
     * @param event 
     */
    @FXML
    private void sorteerAction(ActionEvent event) {
        taOutput.clear(); 
	if(checkLists()){return;}
	
	// Create a new LinkedList to hold the set so we can call .sort().
	// Use a LinkedList because order matters.
	List<String> list = new LinkedList<String>(uniquewords);
	
	// Sort it with a custom Comparator.
	Collections.sort(list, new ReverseAlphabeticalComparator());
	
	// Print results.
	for (String string : list) {
	    taOutput.appendText(string + "\n");
	}	
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Measure frequencies.">
    
    /**
     * Measures the frequency of every unique word 
     * and prints a list of them sorted by frequency.
     * @param event 
     */
    @FXML
    private void frequentieAction(ActionEvent event) {
	taOutput.clear();
	if(checkLists()){return;}
	
	// Keep track of every unique word and it's frequency.
	// LinkedHashMaps, unlike normal HashMaps, keep a tab on the iteration order (eg. FIFO), 
	// while not auto-sorting themselves like TreeMaps. 
	// Reason #2 TreeMaps aren't used is because they typically sort by key instead of value, 
	// unless you hack around with your comparator (by making it lookup the value by it's key during sorting).
	Map<String, Integer> map = new LinkedHashMap();
	
        // Check frequency of every unique word and add it to the map.
	for (String word : uniquewords) {
		int frequency = Collections.frequency(allwords, word);
		map.put(word, frequency);
	}
	
	// Make a list of all the entries in the map so we can let a sorter run through it.
	ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
	
	// Now sort that motherfucker.
	Collections.sort(list, new ValueComparator());
	
	// Make sure to update the map with the sorted entries.
	// Here a foreach is used over an iterator because it's shorter and more readable.
	map.clear();
	for (Map.Entry<String, Integer> entry : list) {
		map.put(entry.getKey(), entry.getValue());
		System.out.println("" + entry.getKey() + "" + entry.getValue());
	}
	
	// Finally, iterate over the map by looking at the entries.
	for (Map.Entry<String, Integer> entry : map.entrySet()) {
		taOutput.appendText(entry.getKey() + ": " + entry.getValue() + "\n");
	}
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Make concordance.">
    
    /**
     * Counts all the locations for every unique word, and prints this per word.
     * @param event 
     */
    @FXML
    private void concordatieAction(ActionEvent event) {
        taOutput.clear();
	if(checkLists()){return;}
	 
	// Get the lines from the text.
	String[] lines = taInput.getText().toLowerCase().split("\n");
	
	// Printable log of the endresults. TreeMap for auto-alpha sort.
	TreeMap<String, List<Integer>> concordance = new TreeMap<>();	
	
	// Start looking for each unique word, the line(s) where it appears in.
	// Save it per word in a new record.
	for (String word : uniquewords) {		
		int linenumber = 0;
		List<Integer> linenumbers = new LinkedList<>();
		
		for(String line : lines){
			if (line.contains(word)) {
				// Zero based linenumbering not allowed.
				linenumbers.add(linenumber + 1);
			}	
			linenumber++;
		}		
		concordance.put(word, linenumbers);
	}	
	
	// And finally print them.
	for (Map.Entry<String, List<Integer>> entry : concordance.entrySet()) {
		// Get all entries from the map, unwrap,
		String word = entry.getKey();
		List<Integer> linenumbers = entry.getValue();
			
		// Combine word with locations into one string for printing.
		String output = word + ": [" + linenumbers.get(0);

		// Starting from 1 so any digits past the first
		// are only registered when available.
		for (int i = 1; i < linenumbers.size(); i++) {
			output += ", " + linenumbers.get(i);
		}

		output += "]\n";

		taOutput.appendText(output);
	}
	
	// Print initial results [the splitted lines] directly to the console for reference.
	for (String line : lines) {
	    System.out.println(line);
	}
    }

    //</editor-fold>
    
    /**
     * Checks if the global lists are empty or not. 
     * If they are, shoots the user a message to calculate the amounts first.
     * @return true if the lists are empty, false otherwise.
     */
    private boolean checkLists(){
	boolean listsareempty = uniquewords.isEmpty();
	if(listsareempty){
		taOutput.appendText("Bereken eerst de aantallen.");
	}
	
	return listsareempty;
    }

    /**
     * Compares strings by reverse alphabetical order.
     */
    class ReverseAlphabeticalComparator implements Comparator<String> {

	public ReverseAlphabeticalComparator() {}
	
	@Override
	public int compare(String s1, String s2) {
		// The minus sign inverts the result, essentially inverting alphabetical comparison.
		return -(s1.compareToIgnoreCase(s2));
	}
    }
    
    /**
     * Compares map entries by value instead of key.
     */
    class ValueComparator implements Comparator<Map.Entry<String, Integer>> {

	public ValueComparator() {}
	
	@Override
	public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
		//return (Integer.compare(m1.getValue(), m2.getValue())); In this case, they're identical.
		return (m1.getValue().compareTo(m2.getValue()));
	}
    }
}
