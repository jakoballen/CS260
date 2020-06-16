/*
 * JavaPrintlnTest - time single Java println
 */
package edu.uwec.cs.cs260.lab8;

public class JavaPrintlnTest implements IDBTimerTest {
	// -- run()
	public void run () {
		DataAccessObject dao = new DataAccessObject();
		Timer aTimer = new Timer();
		
		String resultSetStr = null;							// holder for result set string

		aTimer.startTimer();								// start the timer
		System.out.println("Timing this Java println");
		aTimer.stopTimer();									// stop the timer


		dao.connect();										// establish db connection
		dao.setAutoCommit(false); 							// take control of and start transaction
		dao.executeSQLQuery("SELECT * FROM Account");		// execute query - NOTE: no semicolon at end of query
		resultSetStr = dao.processResultSet();				// process result set (here, convert to string)
		dao.commit();										// commit the transaction if got this far
		dao.disconnect();									// clean up db connection
		
		System.out.println(aTimer.getTotal()/1000000000.0 + " secs"); // display total time
	}	// end - method run()
	
}	// end - class JavaPrintlnTest
