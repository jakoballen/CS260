/*
 * JavaPrintlnTest - time single Java println
 */
package edu.uwec.cs.cs260.lab8;

public class ProcessResultTimerTest implements IDBTimerTest {
	// -- run()
	public void run () {
		DataAccessObject dao = new DataAccessObject();
		Timer aTimer = new Timer();

		String resultSetStr = null;							// holder for result set string

		System.out.println("Timing process result");


		dao.connect();



								// establish db connection
		dao.setAutoCommit(false); 							// take control of and start transaction


		dao.executeSQLQuery("SELECT * FROM Account");		// execute query - NOTE: no semicolon at end of query

		aTimer.startTimer();								// start the timer
		resultSetStr = dao.processResultSet();				// process result set (here, convert to string)
		aTimer.stopTimer();									// stop the timer
		dao.commit();										// commit the transaction if got this far
		dao.disconnect();									// clean up db connection
		
		System.out.println(aTimer.getTotal()/1000000000.0 + " secs"); // display total time
	}	// end - method run()
	
}	// end - class JavaPrintlnTest
