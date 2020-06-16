import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class MongoDAO {
    MongoDatabase database;                     //Mongo DataBase
    MongoClient client;                         //Connection to the database
    MongoCollection<Document> collection;       //Collection in the database
    List<Document> documents;                   //Results from a query

    //Connect to Mongo database
    public void connect(){
        String pw = "24S525Q2";
        MongoCredential credential = MongoCredential.createCredential("ALLENJD4061","CS260", pw.toCharArray());
        client = new MongoClient(new ServerAddress("10.35.195.203", 27017), Arrays.asList(credential));
    }

    //Set the databse being accessed
    public void setDatabase(String database){
        this.database = client.getDatabase(database);
    }

    //Set the collection being accessed from the database
    public void setCollection(String coll){
        collection = database.getCollection(coll);
    }

    //Find all documents in a the current collection
    public void findAll(){
        documents = (List<Document>) collection.find().into(new ArrayList<Document>());
    }

    //Convert documents from a query to a string
    public String processDocuments(){
        String result = "";
        for(Document document : documents) {
            result += document + "\n";
        }
        return result;
    }

    //Insert one document into the collection
    public void insertOne(String jsonString){
        Document doc = Document.parse(jsonString);
        collection.insertOne(doc);
    }

    //Delete one document from the collection given the parameters
    public void deleteEqualTest(String field, Object value){
        collection.deleteMany(eq(field,value));
    }

    //Find documents that fit a certain criteria
    public void findSomeEqual(String field, Object value){
        documents = (List<Document>) collection.find(eq(field, value)).into(new ArrayList<Document>());
    }

    //Delete all elements from the collection
    public void deleteAll(){
        collection.deleteMany(new Document());
    }

    //Disconnect from the Mongo database
    public void disconnect(){
        client.close();
    }
}
