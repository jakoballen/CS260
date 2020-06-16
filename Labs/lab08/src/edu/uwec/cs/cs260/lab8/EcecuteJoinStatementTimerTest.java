/*
 * JavaPrintlnTest - time single Java println
 */
package edu.uwec.cs.cs260.lab8;

public class EcecuteJoinStatementTimerTest implements IDBTimerTest {
	// -- run()
	public void run () {
		DataAccessObject dao = new DataAccessObject();
		Timer aTimer = new Timer();
		
		String resultSetStr = null;							// holder for result set string

		System.out.println("Timing execute statement");


		dao.connect();



								// establish db connection
		dao.setAutoCommit(false); 							// take control of and start transaction

		aTimer.startTimer();								// start the timer
		dao.executeSQLQuery("SELECT * FROM Customer JOIN Account ON (Customer.CustID = Account.Customer) JOIN Transaction ON (Account.AccNumber = Transaction.AccNumber)");		// execute query - NOTE: no semicolon at end of query
		aTimer.stopTimer();									// stop the timer

		resultSetStr = dao.processResultSet();				// process result set (here, convert to string)
		dao.commit();										// commit the transaction if got this far
		dao.disconnect();									// clean up db connection
		
		System.out.println(aTimer.getTotal()/1000000000.0 + " secs"); // display total time
	}	// end - method run()
	
}	// end - class JavaPrintlnTest
