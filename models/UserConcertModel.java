package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         UserConcertModel class
 *
 */
public class UserConcertModel {

	private static final String CONCERTTABLE = "livefy_concerts";
	private static final String USERTABLE = "livefy_users";
	private static final String USERCONCERTTABLE = "livefy_usersconcerts";

	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;
	ResultSet result = null;

	// Constructor
	public UserConcertModel() { // create db object instance
		conn = new DBConnect();
	}

	/**
	 * Create table for concerts
	 */
	public void createUserConcertTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create LivefyUsersConcerts Table...");
			System.out.println("Connected database successfully...");

			stmt = conn.connect().createStatement();

			// Execute create query
			System.out.println("Creating LivefyUsersConcerts table in given database...");

			String sql = "CREATE TABLE IF NOT EXISTS " + USERCONCERTTABLE + "(pid INTEGER not NULL AUTO_INCREMENT, "
					+ "concertname VARCHAR(40), " + "username VARCHAR(30), " + "FOREIGN KEY (`username`) REFERENCES `"
					+ USERTABLE + "` (`username`) ON DELETE CASCADE, UNIQUE(concertname), "
					+ "FOREIGN KEY (`concertname`) REFERENCES `" + CONCERTTABLE
					+ "` (`concertname`) ON DELETE CASCADE, " + "PRIMARY KEY(pid))";
			stmt.executeUpdate(sql);
			System.out.println("Created LivefyUsersConcerts table in given database...");
			conn.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	/**
	 * Retrieve all concerts favourites of the user
	 * 
	 * @param username
	 * @return
	 */
	public ResultSet retrieveConcerts(String username) {
		ResultSet rs = null;
		try {
			System.out.println("Retrieving all concerts favourites from " + username + "...");
			stmt = conn.connect().createStatement();
			String sql = "SELECT concertname FROM " + USERCONCERTTABLE + " WHERE username = '" + username + "'";
			rs = stmt.executeQuery(sql);
			System.out.println("Concerts retrieved...");
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Retrieve all concerts no favourites of the user
	 * 
	 * @param username
	 * @return
	 */
	public ResultSet retrieveNoConcerts(String username) {
		ResultSet rs = null;
		try {
			System.out.println("Retrieving all concerts favourites from " + username + "...");
			stmt = conn.connect().createStatement();
			String sql = "SELECT concertname FROM " + USERCONCERTTABLE + " WHERE username != '" + username + "'";
			rs = stmt.executeQuery(sql);
			System.out.println("Concerts retrieved...");
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Add new favourite concret from user
	 * 
	 * @param user
	 */
	public void insertConcertUser(String concertname, String username) {
		try {
			System.out.println("User " + username + " add to favourites the concert " + concertname + "...");
			stmt = conn.connect().createStatement();
			String sql = null;
			sql = "INSERT INTO " + USERCONCERTTABLE + "(concertname, username) VALUES ('" + concertname + "', '"
					+ username + "' )";
			stmt.executeUpdate(sql);
			System.out.println("Concert " + concertname + " added to favourite list.");
			conn.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Remove new favourite concret from user
	 * 
	 * @param email
	 */
	public void deleteConcertUser(String concertname, String username) {
		try {
			System.out.println(
					"Removing the concert " + concertname + " from the favourite list of the user " + username + "...");
			stmt = conn.connect().createStatement();
			String sql = "DELETE FROM " + USERCONCERTTABLE + " where concertname = '" + concertname
					+ "' AND username = '" + username + "'";
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Concert removed...");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/*
	 * Delete the table
	 * 
	 */
	public void deleteTable() {
		try {
			System.out.println("Deleting table " + USERCONCERTTABLE + " ...");
			stmt = conn.connect().createStatement();
			String sql = "DROP TABLE " + USERCONCERTTABLE;
			stmt.executeUpdate(sql);
			System.out.println("Table " + USERCONCERTTABLE + " deleted...");
			conn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}