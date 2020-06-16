/*
 * Class Timer - class for timing operations in Java, now using System.nanotime
 * 
 * Created by Paul J. Wagner, 5 November 2019
 *
 */
package edu.uwec.cs.cs260.lab8;

public class Timer {
	private long startTime;			// start time in absolute milliseconds
	private long stopTime;			// stop time in absolute milliseconds
	
	// --- default constructor
	public Timer() {
		startTime = 0;
		stopTime = 0;
	}
	
	// --- startTimer - get a starting time
	void startTimer() {
		startTime = System.nanoTime();
	}

	// --- stopTimer - get an ending time
	void stopTimer() {
		stopTime = System.nanoTime();
	}

	// --- getTotal - return the elapsed time
	long getTotal() {
		long result = 0;
		if (stopTime > startTime) {
			result = stopTime - startTime;
		}
		return result;
	}
}	// end - class Timer
