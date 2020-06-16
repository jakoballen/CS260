/*
 * Class TimingTests - timing tests class for aspects of SQL transaction processing
 * 
 * Created by Paul J. Wagner, 6 April 2019
 *
 */
package edu.uwec.cs.cs260.lab8;

import java.util.ArrayList;

public class TimingTests {
	// data
	ArrayList<IDBTimerTest> allTests = null;
	
	// methods
	// -- default constructor
	public TimingTests() {
		allTests = new ArrayList<IDBTimerTest>();
		
		allTests.add(new JavaPrintlnTest());
		allTests.add(new ConnectTimerTest());
		allTests.add(new SetAutoCommitTimerTest());
		allTests.add(new ExecuteStatementTimerTest());
		allTests.add(new ProcessResultTimerTest());
		allTests.add(new CommitTimerTest());
		allTests.add(new EcecuteJoinStatementTimerTest());
		allTests.add(new ProcessResultTimerTest());
		// add more tests here
	}
	
	// -- runAll - run all timing tests
	public void runAll() {
		for (IDBTimerTest test : allTests) {
			for(int i =0; i<5; i++) {
				test.run();
			}
		}
	}
	
}	// end - class TimingTests
