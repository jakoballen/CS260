/*
 * JavaPrintlnTest - time single Java println
 */
package edu.uwec.cs.cs260.lab8;

import java.sql.Connection;
import java.sql.SQLException;

public class SetAutoCommitTimerTest implements IDBTimerTest {
	// -- run()
	public void run () {
		DataAccessObject dao = new DataAccessObject();
		Timer aTimer = new Timer();
		Connection conn = dao.connect();
		String resultSetStr = null;							// holder for result set string

		System.out.println("Timing setting auto-commit to false");

		aTimer.startTimer();								// start the timer
		try {
			dao.daoConn.setAutoCommit(false); 							// take control of and start transaction
			aTimer.stopTimer();									// stop the timer
		}catch(SQLException sqle){

		}


		dao.executeSQLQuery("SELECT * FROM Account");		// execute query - NOTE: no semicolon at end of query
		resultSetStr = dao.processResultSet();				// process result set (here, convert to string)
		dao.commit();										// commit the transaction if got this far
		dao.disconnect();									// clean up db connection
		
		System.out.println(aTimer.getTotal()/1000000000.0 + " secs"); // display total time
	}	// end - method run()
	
}	// end - class JavaPrintlnTest
