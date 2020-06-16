import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Class RuleSet - Class to contain a set of Rule objects
 *
 * Created by: Jakob Allen (allenjd4061), 6 December 2019
 */

public class RuleSet {
    private static final double MAX_LOAD_FACTOR = 0.75;     //Max percentage of filled indices
    private static final int DEFAULT_MAX = 20;              //Default size of array
    private Rule[] rules;                                   //Array of rules
    private int size;                                       //Number of filled indices

    //Basic constructor
    public RuleSet(){
        rules = new Rule[DEFAULT_MAX];
        this.size = 0;
    }

    //Constructor to specified size
    public RuleSet(int size){
        rules = new Rule[size];
        this.size = 0;
    }

    /**
     * Check if the array contains the given rule
     * @param r Rule to check
     * @return boolean representing if the array contains the rule or not
     */
    public boolean contains(Rule r){
        for(int i = 0; i<size; i++){
            if(r.equals(rules[i])){
                return true;
            }
        }
        return false;
    }//end - method contains

    /**
     * Add the given rule to the array
     * @param r Rule to be added
     */
    public void add(Rule r){
        if(loadFactor()>= MAX_LOAD_FACTOR){
            expand();
        }
        if(!contains(r)) {
            rules[size] = r;
            size++;
        }
    }//end - method add

    /**
     * Convert the array to a string
     * @return String representation of the structure
     */
    @Override
    public String toString() {
        String result = "RuleSet{"+ rules[0]; //Start of string representation of rule
        for(int i = 1; i<size; i++){
            result += ", " + rules[i].toString();
        }
        result += "}";
        return result;
    }//end - method toString

    /**
     * Get the element at the given index
     * @param index Index to be checked
     * @return Value at given index
     */
    public Rule get(int index){
        return rules[index];
    }// end - method get

    /**
     * Remove the element at the given index
     * @param index Index of the element to be removed
     */
    public void remove(int index){
        rules[index]  = null;
        size--;
    }//end - method remove

    /**
     * Delete every element from the array
     */
    public void clear(){
        Arrays.fill(rules, null);
        size =0;
    }//end - method clear

    /**
     * Check if structure is empty
     * @return Represent if the array has any filled indices
     */
    public boolean isEmpty(){
        return size() ==0;
    }//end - method isEmpty

    /**
     * Calculate the percentage of filled indices
     * @return percentage of filled indices
     */
    private double loadFactor(){
        return (double) size/rules.length;
    }//end - method loadfactor

    /**
     * Expand the array if it is getting too full
     */
    private void expand(){
        Rule[] old = rules; //Temporary pointer to old array
        rules = new Rule[2 * old.length];
        for(int i = 0; i<old.length; i++){
            rules[i] = old[i];
        }
    }//end - method expand

    /**
     * Report the number of filled indices
     * @return number of filled indices
     */
    public int size(){
        return size;
    }//end - method size

    /**
     * Convert the RuleSet into a list of documents
     * @return List of documents equivalent to the RuleSet
     */
    public List<Document> toDocumentList(){
        List<Document> docs = new ArrayList<>(); //Document List version of RuleSet
        for(int i = 0; i<size; i++){
            docs.add(rules[i].toDocument());
        }
        return docs;
    }//end - method toDocumentList

    /**
     * Combine the current RuleSet with another
     * @param rs The other RuleSet to be added
     */
    public void addOtherRuleSet(RuleSet rs){
        for(int i = 0; i<rs.size(); i++){
            add(rs.get(i));
        }
    }//end - method addOtherRuleSet

}//end - class RuleSet
