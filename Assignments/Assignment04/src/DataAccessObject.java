import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Class DataAccessObject - Class to hold all MongoDB references and methods
 *
 * Created by: Jakob Allen (allenjd4061), 6 December 2019
 */

public class DataAccessObject {
    MongoDatabase database;                     //Mongo DataBase
    MongoClient client;                         //Connection to the database
    MongoCollection<Document> collection;       //Collection in the database
    List<Document> documents;                   //Results from a query

    /**
     * Connect to Mongo database
     */
    public void connect(){
        String pw = "24S525Q2";
        MongoCredential credential = MongoCredential.createCredential("ALLENJD4061","CS260", pw.toCharArray());
        client = new MongoClient(new ServerAddress("10.35.195.203", 27017), Arrays.asList(credential));
    }//end - method connect

    /**
     * Set the database being accessed
     */
    public void setDatabase(String database){
        this.database = client.getDatabase(database);
    }//end - method setDatabase

    /**
     * Set the collection being accessed from the database
     */
    public void setCollection(String coll){
        collection = database.getCollection(coll);
    }//end - method setCollection

    /**
     * Process the results from a previous query
     * @return RuleSet containing the rules from the query
     */
    public RuleSet processResults(){
        RuleSet rules = new RuleSet(2 * documents.size()); //RuleSet to contain the rules from the database
        for(Document document : documents) {
            Rule r = new Rule(document.getInteger("r_id"),document.getDate("r_datetime"),(ArrayList<String>)document.get("r_lhs"),(ArrayList<String>)document.get("r_rhs")); //Rule object for database document
            rules.add(r);
        }
        return rules;
    }//end - method processResults

    /**
     * Find all the documents in the current collection
     */
    public void executeQueryAll(){
        documents = (List<Document>) collection.find().into(new ArrayList<Document>());
    }//end - method executeQueryAll

    /**
     * Find all the documents within a given range
     * @param min smallest value
     * @param max largest value
     */
    public void executeQueryIDRange(int min, int max){
        BasicDBObject query = new BasicDBObject();  //Query to be processed
        query.put("r_id", BasicDBObjectBuilder.start("$gte", min).add("$lte", max).get());
        documents = (List<Document>) collection.find(query).into(new ArrayList<Document>());
    }//end - method executeQueryIDRange

    /**
     * Find all the documents created within a given range
     * @param min smallest value
     * @param max largest value
     */
    public void executeQueryDateRange(Date min, Date max){
        BasicDBObject dateRange = new BasicDBObject ("$gte", min);  //Contains the range of dates
        dateRange.put("$lte", max);
        BasicDBObject query = new BasicDBObject("r_datetime", dateRange);   //Query to be processed
        documents = (List<Document>) collection.find(query).into(new ArrayList<Document>());
    }//end - method executeQueryDateRange

    /**
     * Add the entire RuleSet to the current collection
     * @param rs RuleSet to be added
     */
    public void addManyRules(RuleSet rs){
        collection.insertMany(rs.toDocumentList());
    }//end - method addManyRule

    /**
     * Add a rule to the database
     * @param r Rule to be added
     */
    public void addRule(Rule r){
        collection.insertOne(r.toDocument());
    }//end -  method addRule

    /**
     * Delete all elements from the collection
     */
    public void deleteAll(){
        collection.deleteMany(new Document());
    }//end - method deleteAll

    /**
     * Find the next available id for a rule
     * @return int representing the next free ID value
     */
    public int nextIndex(){
        FindIterable<Document> next = client.getDatabase("ALLENJD4061").getCollection("RulesOutput").find().sort(new BasicDBObject("r_id", -1)).limit(1);
        for(Document doc: next){
            return doc.getInteger("r_id") + 1;
        }
        return -1;
    }//end - method nextIndex

    /**
     * Disconnect from the database
     */
    public void disconnect(){
        client.close();
    }//end - method disconnect

}// end - class DataAccessObject
