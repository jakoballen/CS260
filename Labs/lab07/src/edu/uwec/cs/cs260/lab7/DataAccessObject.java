/*
 * Class DataAccessObject - class to hold all Oracle database references and methods
 * 
 * Created by Paul J. Wagner, 25 March 2019
 *
 */
package edu.uwec.cs.cs260.lab7;

import java.sql.*;

public class DataAccessObject {

	private Connection conn = null;			// JDBC connection
	private ResultSet rset = null;			// result set for queries
	private int returnValue;				// return value for all other commands
		
	// --- connect() - connect to the Oracle database
	public Connection connect() throws SQLException {
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
		   conn = DriverManager.getConnection(
		   "jdbc:oracle:thin:@alfred.cs.uwec.edu:1521:csdev",user,pass);
		}
		catch (SQLException sqle) {
		   System.err.println ("Could not make connection to database");
		   throw new SQLException();
		}
		return conn;
	}	// end - method connect
	
	// --- executeSQLQuery() - execute an SQL query
	public ResultSet executeSQLQuery (String sqlQuery) throws SQLException {
		// --- 3a) execute SQL query
		Statement stmt = null;		// SQL statement object
		rset = null;				// initialize result set
		
		try	{
		   stmt = conn.createStatement();
		   rset = stmt.executeQuery(sqlQuery);
		}
		catch (SQLException e) {
			System.err.println("Could not execute SQL statement: >" + sqlQuery + "<");
			throw new SQLException();
		}
		return rset;
	}	// end - method executeSQLQuery
	
	// --- executeSQLNonQuery() - execute an SQL command that is not a query
	public int executeSQLNonQuery (String sqlCommand) throws SQLException {
		// --- 3b) execute SQL non-query command
		Statement stmt = null;		// SQL statement object
		returnValue = -1;			// initialize return value
		try	{
		   stmt = conn.createStatement();
		   returnValue = stmt.executeUpdate(sqlCommand);
		}
		catch (SQLException e) {
			System.err.println("Could not execute SQL command: >" + sqlCommand + "<");
			System.err.println("Return value: " + returnValue);
			System.err.println("Message: " + e.getMessage());
			throw new SQLException();
		}
		return returnValue;
	}	// end - method executeSQLNonQuery
	
	// --- processResultSet() - process the result set
	public String processResultSet (ResultSet rset) throws SQLException, NullPointerException {
		// --- 4) process result set, only applicable if executing an SQL SELECT statement
		ResultSetMetaData rsmd = null;		// result set metadata object
		int columnCount = -1;				// column count 
		String resultString = "";			// result string 
		
		try {
			rsmd = rset.getMetaData();
			
			// get number of columns from result set metadata
			columnCount = rsmd.getColumnCount();
			
			// row processing of result set
			while (rset.next()) {
				for (int index = 1; index <= columnCount; index++) {
					resultString += rset.getString(index) + "  ";
				}
				resultString += "\n";
			}
		}
		catch (SQLException sqle) {
			System.err.println("Error in processing result set");
			throw new SQLException();
		}
		catch (NullPointerException npe) {
			System.err.println("DAO, processResultSet() - no result set generated");
			throw new NullPointerException();
		}
		return resultString;
	}	// end - method processResultSet
	
	// --- disconnect() - disconnect from the Oracle database
	public void disconnect () throws SQLException {
		// --- 5) disconnect from database
		try {
			if (conn != null) {
				conn.close();
			}
			if (rset != null) {
				rset = null;
			}
		}
		catch (SQLException sqle) {
			System.err.println ("Error in closing database connection");
			throw new SQLException();
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException sqle) {
					conn = null;
				}
			}
			if (rset != null) {
				try {
					rset = null;
				}
				catch (Exception e) {
					rset = null;
				}
			}
		}
	}	// end - method disconnect

}	// end - class DataAccessObject
