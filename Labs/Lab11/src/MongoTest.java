/*
 * MongoTest - main program to test connection to MongoDB
		 *
		 * Last updated 05-DEC-2019, Paul J. Wagner
		 */

public class MongoTest<dao> {

	public static void main(String[] args) {
		MongoDAO dao = new MongoDAO();
		dao.connect();
		dao.setDatabase("CS260");
		dao.setCollection("exampleCollection");
		dao.findAll();
		String result = dao.processDocuments();
		System.out.println("find result is: " + result);

		dao.setDatabase("ALLENJD4061");
		dao.setCollection("Movies");
		dao.deleteAll();
		dao.insertOne("{ 'title' : 'Before The Rain' , 'year' : 1994 }");
		dao.findAll();
		result = dao.processDocuments();
		System.out.println("1: "+result);
		dao.insertOne("{'title' : 'Avengers: Endgame' , 'year' : 2019 , 'genre' : [ 'action' , 'adventure' , 'fantasy' ] , 'directors' : [ 'Anthony Russo' , 'Joe Russo' ] }");
		dao.findAll();
		result = dao.processDocuments();
		System.out.println("2: "+result);
		dao.findSomeEqual("title", "Avengers: Endgame");
		result = dao.processDocuments();
		System.out.println("3: "+result);
		dao.deleteEqualTest("title", "Before The Rain");
		result = dao.processDocuments();
		System.out.println("4: "+result);
		dao.disconnect();

	}	// end - main method
}	// end - class MongoTest