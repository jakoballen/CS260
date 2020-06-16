/*
 * Class DataAccessObject - class to hold all Oracle database references and methods
 * 
 * Created by Paul J. Wagner, 25 March 2019
 * Modified by Jakob Allen, 20 November 2019
 *
 */


import java.sql.*;
import java.util.ArrayList;

public class DataAccessObject {

	private Connection daoConn = null;			// JDBC connection
	private ResultSet  daoRset = null;			// result set for queries
	private int returnValue;					// return value for all other commands

	// --- connect() - connect to the Oracle database
	public Connection connect() {
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
		   setAutoCommit(false);
		   }
		catch (SQLException sqle) {
		   System.err.println ("Could not make connection to database");
		   System.err.println(sqle.getMessage());
		}
		return daoConn;
	}	// end - method connect
	
	// --- executeSQLQuery() - execute an SQL query
	public ResultSet executeSQLQuery (String sqlQuery) {
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
		return daoRset;
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

	/**
	 * Find all HRID currently in the database.
	 * @return An ArrayList containing all HRIDs in the database table HumResource.
	 */
	public ArrayList<Integer> getUsedHRID(HumResource hr){
		String table = null; //table to select from
		if(hr instanceof Water){
			table = "Water";
		}else if(hr instanceof Food){
			table = "Food";
		}else if(hr instanceof MedicalCenter){
			table = "MedicalCenter";
		}else{
			table = "HumResource";
		}

		ArrayList<Integer> availHRID = new ArrayList<>();
		try{
			ResultSet rset = executeSQLQuery("SELECT HRID FROM "+table);
			while(rset.next()){
				availHRID.add(rset.getInt("HRID"));
			}

		}catch (SQLException sqle) {
			System.err.println("Error in transaction, rolling back...");
			rollback();										// rollback if problem above
		}

		return availHRID;
	}

	/**
	 * Finds the next available HRID in the database
	 * @return An int for the next unused HRID
	 */
	public int getNextHRID(){
		ArrayList<Integer> availHRID = getUsedHRID(new HumResource()); //list of IDs currently in the databse
		return availHRID.get(availHRID.size()-1)+1;
	}
	/**
	 * Set this object's fields to data pulled from the database.
	 * @param hr the HRID of the desired information.
	 */
	public void populateData(HumResource hr){
		try {
			daoRset = executeSQLQuery("SELECT * FROM HumResource WHERE HRID = " + hr.getHRID());
			while(daoRset.next()) {
				hr.setHRID(daoRset.getInt("HRID"));
				hr.setHRName(daoRset.getString("HRName"));
				hr.setHRAddress(daoRset.getString("HRAddressString"));
				hr.setHRPhoneNumber(daoRset.getString("HRPhoneNumber"));
				hr.setLatitude(daoRset.getDouble("HRLatitude"));
				hr.setLongitude(daoRset.getDouble("HRLongitude"));
				hr.setHRType(daoRset.getString("HRType"));
				hr.setHRDesc(daoRset.getString("HRDesc"));
				hr.setHROpenHours(daoRset.getString("HROpenHoursString"));
			}
			if(hr instanceof Water){
				daoRset = executeSQLQuery("SELECT * FROM Water WHERE HRID = " + hr.getHRID());
				while(daoRset.next()) {
					((Water)hr).setNum10ozBottle(daoRset.getInt("Num10ozBottlesAvailable"));
					((Water)hr).setNumHalfLiterBottle(daoRset.getInt("NumHalfLiterBottlesAvailable"));
					((Water)hr).setNum5GalJug(daoRset.getInt("Num5GallonJugsAvailable"));
				}
			}else if(hr instanceof Food){
				daoRset = executeSQLQuery("SELECT * FROM Food WHERE HRID = " + hr.getHRID());
				while(daoRset.next()) {
					((Food)hr).setfType(daoRset.getString("FType"));
					((Food)hr).setfMealsAvail(daoRset.getInt("FMealsAvailable"));
					((Food)hr).setfSpecificDesc(daoRset.getString("FSpecificDesc"));
				}
			}else if(hr instanceof MedicalCenter){
				daoRset = executeSQLQuery("SELECT * FROM MedicalCenter WHERE HRID = " + hr.getHRID());
				while(daoRset.next()) {
					((MedicalCenter) hr).setNumBeds(daoRset.getInt("NumBeds"));
					((MedicalCenter) hr).setEmergencyRoomCap(daoRset.getInt("EmergencyRoomCapacity"));
					((MedicalCenter) hr).setNumDoctors(daoRset.getInt("NumDoctors"));
					((MedicalCenter) hr).setNumNurses(daoRset.getInt("NumNurses"));
				}
			}
		}catch (SQLException sqle) {
			System.err.println("Error in transaction, rolling back...");
			rollback();
		}
	}

	/**
	 * Update the database with the current values of this object.
	 * @return Number of table affected.
	 */
	public int update(HumResource hr){
		int rows = 0; //number of rows affected
		String sqlHR = "UPDATE HUMRESOURCE SET HRNAME='"+hr.getHRName()+"', HRADDRESSSTRING='"+hr.getHRAddress()+"', HRPHONENUMBER='"+hr.getHRPhoneNumber()+"', HRLATITUDE="+hr.getLatitude()+", HRLONGITUDE="+hr.getLongitude()+", HRTYPE='"+hr.getHRType()+"', HRDESC='"+hr.getHRDesc()+"', HROPENHOURSSTRING='"+hr.getHROpenHours()+"' WHERE HRID="+ hr.getHRID();
		rows += executeSQLNonQuery(sqlHR);
		if(hr instanceof MedicalCenter) {
			String sqlMC = "UPDATE MEDICALCENTER SET NumBeds =" + ((MedicalCenter) hr).getNumBeds() + ", EmergencyRoomCapacity=" + ((MedicalCenter) hr).getEmergencyRoomCap() + ",NumDoctors=" + ((MedicalCenter) hr).getNumDoctors() + ",NumNurses=" + ((MedicalCenter) hr).getNumNurses() + " WHERE HRID=" + hr.getHRID();
			rows += executeSQLNonQuery(sqlMC);
		}else if(hr instanceof Food){
			String sqlF = "UPDATE Food SET FType='" + ((Food)hr).getfType() + "', FMealsAvailable=" + ((Food)hr).getfMealsAvail() + ", FSpecificDesc='" + ((Food)hr).getfSpecificDesc() + "' WHERE HRID="+ hr.getHRID();
			rows += executeSQLNonQuery(sqlF);
		}else if(hr instanceof Water){
			String sqlW = "UPDATE WATER SET Num10OzBottlesAvailable=" + ((Water)hr).getNum10ozBottle() + ", NumHalfLiterBottlesAvailable=" + ((Water)hr).getNumHalfLiterBottle() + ", Num5GallonJugsAvailable=" + ((Water)hr).getNum5GalJug() + " WHERE HRID="+ hr.getHRID();
			rows += executeSQLNonQuery(sqlW);
		}

		if(rows == 2){
			commit(); //commit if both actions are successful
		}else{
			rollback();
			rows = 0;
		}

		return rows;
	}

	/**
	 * Insert this objects values into the database.
	 * @return Number of rows affected.
	 */
	public int insert(HumResource hr) {
		int rows = 0; //number of rows affected
		String sqlHR = "INSERT INTO HumResource VALUES(" + hr.getHRID() + ",'" + hr.getHRName() + "','" + hr.getHRAddress() + "','" + hr.getHRPhoneNumber() + "'," + hr.getLatitude() + "," + hr.getLongitude() + ",'" + hr.getHRType() + "','" + hr.getHRDesc() + "','" + hr.getHROpenHours() + "')";
		rows += executeSQLNonQuery(sqlHR);
		if(hr instanceof MedicalCenter){
			String sqlMC = "INSERT INTO MedicalCenter VALUES(" + hr.getHRID() + "," + ((MedicalCenter)hr).getNumBeds() + "," + ((MedicalCenter)hr).getEmergencyRoomCap() + "," + ((MedicalCenter)hr).getNumDoctors() + "," + ((MedicalCenter)hr).getNumNurses()+")";
			rows += executeSQLNonQuery(sqlMC);
		}else if(hr instanceof Food){
			String sqlF = "INSERT INTO FOOD VALUES(" + hr.getHRID() + ",'" + ((Food)hr).getfType() + "'," + ((Food)hr).getfMealsAvail() + ",'" + ((Food)hr).getfSpecificDesc() + "')";
			rows += executeSQLNonQuery(sqlF);
		}else if(hr instanceof Water){
			String sqlW = "INSERT INTO WATER VALUES(" + hr.getHRID() + "," + ((Water)hr).getNum10ozBottle() + "," + ((Water)hr).getNumHalfLiterBottle() + "," + ((Water)hr).getNum5GalJug() + ")";
			rows += executeSQLNonQuery(sqlW);
		}

		if(rows == 2){
			commit(); //commit if both actions are successful
			return rows;
		}else{
			rollback();
			rows = 0;
		}

		return rows;
	}

	/**
	 * Delete the row of the database with this HRID.
	 * @return Number of rows affected.
	 */
	public int delete(HumResource hr) {
		int rows = 0; //number of rows affected
		if(hr instanceof MedicalCenter){
			rows += executeSQLNonQuery("delete from MEDICALCENTER where hrid=" + hr.getHRID());
		}else if(hr instanceof Food){
			rows += executeSQLNonQuery("delete from FOOD where hrid=" + hr.getHRID());
		}else if(hr instanceof Water){
			rows += executeSQLNonQuery("delete from WATER where hrid=" + hr.getHRID());
		}
		rows += executeSQLNonQuery("delete from humresource where hrid=" + hr.getHRID());


		if(rows == 2){
			commit(); //commit if both actions are successful
		}else{
			rollback();
			rows = 0;
		}

		return rows;
	}
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
