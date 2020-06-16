/*
 * Class DataAccessObject - class to hold all Oracle database references and methods
 * 
 * Created by Paul J. Wagner, 25 March 2019
 *
 */

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DataAccessObject {

	private Connection daoConn = null;			// JDBC connection
	private ResultSet  daoRset = null;			// result set for queries
	private int returnValue;					// return value for all other commands
		
	// --- connect() - connect to the Oracle database
	public void connect() {
		// --- set the username and password
		String user = "ALLENJD4061";
		String pass = "IKF53ZJK";
		
		// --- 1) get the Class object for the driver 
		try {
		   Class.forName ("oracle.jdbc.OracleDriver");
		}
		catch (ClassNotFoundException e) {
		   System.err.println ("Could not get class object for Driver");
		}

		// --- 2) connect to database
		try {
		   daoConn = DriverManager.getConnection(
		   "jdbc:oracle:thin:@alfred.cs.uwec.edu:1521:csdev",user,pass);
		   }
		catch (SQLException sqle) {
		   System.err.println ("Could not make connection to database");
		   System.err.println(sqle.getMessage());
		}
	}	// end - method connect
	
	// --- executeSQLQuery() - execute an SQL query
	public void executeSQLQuery (String sqlQuery) {
		// --- 3a) execute SQL query
		Statement stmt = null;		// SQL statement object
		daoRset = null;				// initialize result set
		
		try	{
		   stmt = daoConn.createStatement();
		   daoRset = stmt.executeQuery(sqlQuery);
		}
		catch (SQLException sqle) {
			System.err.println("Could not execute SQL statement: >" + sqlQuery + "<");
			System.err.println(sqle.getMessage());
			// rollback
			rollback();
		}
	}	// end - method executeSQLQuery
	
	// --- executeSQLNonQuery() - execute an SQL command that is not a query
	public int executeSQLNonQuery (String sqlCommand) {
		// --- 3b) execute SQL non-query command
		Statement stmt = null;		// SQL statement object
		returnValue = -1;			// initialize return value
		try	{
		   stmt = daoConn.createStatement();
		   returnValue = stmt.executeUpdate(sqlCommand);
		}
		catch (SQLException sqle) {
			System.err.println("Could not execute SQL command: >" + sqlCommand + "<");
			System.err.println("Return value: " + returnValue);
			System.err.println(sqle.getMessage());
			// rollback
			rollback();
		}
		return returnValue;
	}	// end - method executeSQLNonQuery
	
	// --- processResultSet() - process the result set
	public String processResultSet () {
		// --- 4) process result set, only applicable if executing an SQL SELECT statement
		ResultSetMetaData rsmd = null;		// result set metadata object
		int columnCount = -1;				// column count 
		String resultString = "";			// result string 
		
		try {
			rsmd = daoRset.getMetaData();
			
			// get number of columns from result set metadata
			columnCount = rsmd.getColumnCount();
			
			// row processing of result set
			while (daoRset.next()) {
				for (int index = 1; index <= columnCount; index++) {
					resultString += daoRset.getString(index) + "  ";
				}
				resultString += "\n";
			}
		}
		catch (SQLException sqle) {
			System.err.println("Error in processing result set");
			System.err.println(sqle.getMessage());
		}
		catch (NullPointerException npe) {
			System.err.println("DAO, processResultSet() - no result set generated");
			System.err.println(npe.getMessage());
		}
		return resultString;
	}	// end - method processResultSet

	// --- processResultSetHRObjects() - process the result set, returning list of HumResource objects
	public List<HumResource> processResultSetHRObjects () {
		// --- 4) process result set to objects, only applicable if executing an SQL SELECT statement
		List<HumResource> resourceList = new ArrayList<HumResource>();	// list of resources to return
		HumResource currHR = null;							// current hum. resource to process

		try {
			// row processing of result set
			while (daoRset.next()) {
				currHR = new HumResource();							// set up a default HumResource object
				currHR.setHrID(daoRset.getInt(1));					// 1st field is HRID
				currHR.setHrName(daoRset.getString(2));	 			// 2nd field is HR name
				currHR.setHrAddressString(daoRset.getString(3));	// 3rd field is HR address string
				currHR.setHrPhoneNumber(daoRset.getString(4)); 		// 4th field is HR phone
				currHR.setHrLatitude(daoRset.getDouble(5));	 		// 5th field is HR latitude
				currHR.setHrLongitude(daoRset.getDouble(6));				// 6th field is HR longitude
				currHR.setHrType(daoRset.getString(7)); 					// 7th field is HR type
				currHR.setHrDesc(daoRset.getString(8));   			// 8th field is HR description
				currHR.setHrOpenHoursString(daoRset.getString(9));  // 9th field is HR open hours string
				resourceList.add(currHR);
			}
		}
		catch (SQLException sqle) {
			System.err.println("Error in processing result set");
			System.err.println(sqle.getMessage());
		}
		catch (NullPointerException npe) {
			System.err.println("DAO, processResultSet() - no result set generated");
			System.err.println(npe.getMessage());
		}
		return resourceList;
	}	// end - method processResultSetHRObjects

	// --- setAutoCommit(flag) - set autocommit on or off based on flag
	public void setAutoCommit (boolean flag) {
		try {
			daoConn.setAutoCommit(flag);
		}
		catch (SQLException sqle) {
			System.err.println("DAO, setAutoCommit() - error in setting");
			System.err.println(sqle.getMessage());
		}
	}	// end - method setAutoCommit()
	
	// --- commit() - commit current transaction on connection
	public void commit () {
		try {
			daoConn.commit();
		}
		catch (SQLException sqle) {
			System.err.println("DAO, commit() - error in commit");
			System.err.println(sqle.getMessage());
		}
	}	// end - method commit()

	// --- rollback() - rollback current transactino on connection
	public void rollback () {
		try {
			daoConn.rollback();
		}
		catch (SQLException sqle) {
			System.err.println("DAO, rollback() - error in rollback");
			System.err.println(sqle.getMessage());
		}
	}	// end - method rollback()
	
	// --- disconnect() - disconnect from the Oracle database
	public void disconnect () {
		// --- 5) disconnect from database
		try {
			if (daoConn != null) {
				daoConn.close();
			}
			if (daoRset != null) {
				daoRset = null;
			}
		}
		catch (SQLException sqle) {
			System.err.println ("Error in closing database connection");
			System.err.println(sqle.getMessage());
		}
		finally {
			if (daoConn != null) {
				try {
					daoConn.close();
				}
				catch (SQLException sqlerb) {
					daoConn = null;
				}
			}
			if (daoRset != null) {
				try {
					daoRset = null;
				}
				catch (Exception e) {
					daoRset = null;
				}
			}
		}
	}	// end - method disconnect

}	// end - class DataAccessObject
