/*
 * Class TransactionTest - test class for SQL transactions
 * 
 * Created by Paul J. Wagner, 25 March 2019
 *
 */
package edu.uwec.cs.cs260.lab7;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionTest {
	// data
	DataAccessObject dao = null;		// our code to connect to Oracle
	Connection conn = null;				// a JDBC connection object
	ResultSet rset = null;				// a JDBC result set object
	String resultSetStr = null;			// a string to hold the SQL query output
	
	// methods
	// -- default constructor
	public TransactionTest() {
		dao = new DataAccessObject();
	}

	// -- runTest1() - basic connection and query test
	public void runTest1() {
		System.out.println("Test 1 - Starting transaction...");
		
		try {
			conn = dao.connect();										// establish db connection
			conn.setAutoCommit(false);
			rset = dao.executeSQLQuery("SELECT * FROM Account");		// execute query - NOTE: no semicolon at end of query
			resultSetStr = dao.processResultSet(rset);					// process result set (here, convert to string)
			conn.commit();												// commit the transaction if got this far
			System.out.println("Test 1 - Displaying Account table: \n" + resultSetStr);
			System.out.println("Test 1 - Transaction committed\n");
			dao.disconnect();											// clean up db connection
		} catch (SQLException sqle) {
			System.err.println("Test 1 - Error in transaction, rolling back...");
			try {
				conn.rollback();										// rollback if problem above
			}
			catch (SQLException sqlerb) {								// if possible error during rollback
				System.err.println("Test 1 - Error in transaction rollback, notifying user...");
				System.out.println("Test 1 - Error in rollback, data may be inconsistent,,,");
			}
		}
	}	// end - method runTest1
	
	// -- runTest2() - transaction simulating account creation - add new customer, add new account, add transaction deposit
	public void runTest2() {
		System.out.println("Test 2 - Starting transaction...");
		int insertResult;												// return value from SQL insert
		
		try {
			// establish db connection
			conn = dao.connect();										// establish db connection
			conn.setAutoCommit(false);

			//insertResult = dao.executeSQLNonQuery("DELETE FROM Transaction WHERE TransID = 2000");
			//insertResult = dao.executeSQLNonQuery("DELETE FROM Account WHERE ACCNumber = 146921");
			//insertResult = dao.executeSQLNonQuery("DELETE FROM Customer WHERE CustID = 198236981");


			// execute statement 1 - insert row in Customer table
			//    use the values: 198236981, Alix, Kanazawa, 4242
			dao.executeSQLNonQuery("INSERT INTO Customer " + "VALUES (198236981, 'Alix', 'Kanazawa', 4242)");


			
			// execute statement 2 - insert row in Account table
			//    use the values: 146921, savings, 0.0, 198236981, Central, SYSDATE, null, Active
			dao.executeSQLNonQuery("INSERT INTO Account " + "VALUES (146921, 'savings', 0.0, 198236981, 'Central', SYSDATE, null, 'Active')");

			
			// execute statement 3 - insert row in Transaction table (deposit 500 US dollars)
			//    use the values: 2000, 146921, 500, SYSDATE, 'd', Central
			dao.executeSQLNonQuery("INSERT INTO Transaction " + "VALUES (2000, 146921, 500, SYSDATE, 'd', 'Central')");

			// informational output
			System.out.println("Test 2 - All inserts completed, but not yet committed");
			
			// commit the transaction
			conn.commit();


			// informational output
			System.out.println("Test 2 - Transaction committed\n");
			
			// clean up db connection
			dao.disconnect();

		
		} catch (SQLException sqle) {
			System.err.println("Test 2 - Error in transaction, rolling back...");
			try {
				conn.rollback();										// rollback if problem above
			}
			catch (SQLException sqlerb) {								// if possible error during rollback
				System.err.println("Test 2 - Error in transaction rollback, notifying user...");
				System.out.println("Test 2 - Error in rollback, data may be inconsistent...");
			}
		}
	}	// end - method runTest2	
	
}	// end - class TransactionTest
