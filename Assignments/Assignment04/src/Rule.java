import org.bson.Document;

import java.util.*;

/**
 * Class Rule - Class to represent a rule
 *
 * Created by: Jakob Allen (allenjd4061), 6 December 2019
 */
public class Rule {
    private int r_id;                   //Rule ID
    private Date r_datetime;            //Date and Time the rule was created
    private ArrayList<String> r_lhs;    //Left side elements
    private ArrayList<String> r_rhs;    //Right side elements

    public Rule(int r_id, Date r_datetime, ArrayList<String> r_lhs, ArrayList<String> r_rhs) {
        this.r_id = r_id;
        this.r_datetime = r_datetime;
        this.r_lhs = r_lhs;
        this.r_rhs = r_rhs;
        Collections.sort(r_lhs);
        Collections.sort(r_rhs);
    }

    //getters and setters
    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public Date getR_datetime() {
        return r_datetime;
    }

    public void setR_datetime(Date r_datetime) {
        this.r_datetime = r_datetime;
    }

    public ArrayList<String> getR_lhs() {
        return r_lhs;
    }

    public void setR_lhs(ArrayList<String> r_lhs) {
        this.r_lhs = r_lhs;
        Collections.sort(this.r_lhs);
    }

    public ArrayList<String> getR_rhs() {
        return r_rhs;
    }

    public void setR_rhs(ArrayList<String> r_rhs) {
        this.r_rhs = r_rhs;
        Collections.sort(this.r_rhs);
    }

    /**
     * Convert the Rule to a string
     * @return String representation of the Rule
     */
    @Override
    public String toString() {
        String result = "IF ";  //Start of string representation of rule
        for(String str: r_lhs){
            if(str.equals(r_lhs.get(0))){
                result += str;
            }else{
                result += " AND "+str;
            }
        }
        for(String str: r_rhs){
            if(str.equals(r_rhs.get(0))){
                result += " THEN "+str;
            }else{
                result += " AND "+str;
            }
        }
        return result;
    }//end - method toString

    /**
     * Reports whether or not the given object is equal
     * @param o other object
     * @return boolean stating if the object is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(r_lhs, rule.r_lhs) &&
                Objects.equals(r_rhs, rule.r_rhs);
    }//end - method equals

    /**
     * Convert the Rule into a document
     * @return Document representing the rule
     */
    public Document toDocument(){
        Document document = new Document("r_id",r_id );     //Start of document version of Rule
        document.append("r_datetime", r_datetime);
        document.append("r_lhs", r_lhs);
        document.append("r_rhs", r_rhs);
        return document;
    }//end - method toDocument

    /**
     * Reports whether or not the Rule contains the same element on both sides
     * @return boolean telling the user if there is a duplicate element
     */
    public boolean elementOnBothSides(){
        return r_rhs.containsAll(r_lhs) || r_lhs.containsAll(r_rhs);
    }//end - method elementOnBothSides

}// end -- class Rule
