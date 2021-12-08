package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import resources.Concert;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         ConcertModel class
 *
 */
public class ConcertModel {

	private static final String CONCERTTABLE = "livefy_concerts";
	private static final String USERTABLE = "livefy_users";

	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;
	ResultSet result = null;

	// Constructor
	public ConcertModel() { // create db object instance
		conn = new DBConnect();
	}

	/**
	 * Create table for concerts
	 */
	public void createConcertTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create LivefyConcerts Table...");
			System.out.println("Connected database successfully...");

			stmt = conn.connect().createStatement();

			// Execute create query
			System.out.println("Creating LivefyConcerts table in given database...");

			String sql = "CREATE TABLE IF NOT EXISTS " + CONCERTTABLE + "(concertname VARCHAR(40) not null, "
					+ " date DATE, " + "createdDate TIMESTAMP, " + "description VARCHAR(500), "
					+ "completed BOOLEAN DEFAULT false, " + "artist VARCHAR(30), "
					+ "FOREIGN KEY (`artist`) REFERENCES `" + USERTABLE
					+ "` (`username`) ON DELETE CASCADE, UNIQUE(concertname), " + "PRIMARY KEY(concertname))";
			stmt.executeUpdate(sql);
			System.out.println("Created LivefyConcerts table in given database...");
			conn.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	/**
	 * Retrieve all concerts information
	 * 
	 * @return
	 */
	public ResultSet retrieveConcerts() {
		ResultSet rs = null;
		try {
			System.out.println("Retrieving all concerts information...");
			stmt = conn.connect().createStatement();
			String sql = "SELECT concertname, date, createdDate, description, completed, artist FROM " + CONCERTTABLE
					+ " order BY completed ASC";
			rs = stmt.executeQuery(sql);
			System.out.println("Concerts retrieved...");
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet retrieveConcert(String concertname) {
		ResultSet rs = null;
		try {
			stmt = conn.connect().createStatement();
			String sql = "SELECT concertname, date, createdDate, description, completed, artist FROM " + CONCERTTABLE
					+ " WHERE concertname = '" + concertname + "'order BY completed ASC";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Retrieve all concerts information
	 * 
	 * @return
	 */
	public ResultSet retrieveConcertsArtist(String username) {
		ResultSet rs = null;
		try {
			System.out.println("Retrieving all concerts information...");
			stmt = conn.connect().createStatement();
			String sql = "SELECT concertname, date, createdDate, description, completed, artist FROM " + CONCERTTABLE
					+ " WHERE artist = '" + username + "' " + " order BY completed ASC";
			rs = stmt.executeQuery(sql);
			System.out.println("Concerts retrieved...");
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Add new concert
	 * 
	 * @param user
	 */
	public void insertConcert(Concert concert) {
		try {
			System.out.println("Inserting concert " + concert.getConcertname() + " into the table...");
			stmt = conn.connect().createStatement();
			String sql = null;

			sql = "INSERT INTO " + CONCERTTABLE
					+ "(concertname, date, createdDate, description, completed, artist) VALUES ('"
					+ concert.getConcertname() + "', '" + concert.getDate() + "', '" + concert.getCreatedDate() + "', '"
					+ concert.getDescription() + "', '" + concert.getCompleted() + "', '" + concert.getArtistname()
					+ "' )";

			stmt.executeUpdate(sql);

			System.out.println("Concert " + concert.getConcertname() + " inserted into LivefyConcerts.");
			conn.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Delete concert
	 * 
	 * @param email
	 */
	public void deleteConcert(String concertName) {
		try {
			System.out.println("Deleteing the concert " + concertName + " ...");
			stmt = conn.connect().createStatement();
			String sql = "DELETE FROM " + CONCERTTABLE + " where concertname = '" + concertName + "'";
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Concert deleted...");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Check if concertName already exits. Return true if it exits
	 * 
	 * @param concertname
	 * @return
	 */
	public boolean checkConcertname(String concertname) {
		try {
			System.out.println("Checking concertName " + concertname + "...");
			stmt = conn.connect().createStatement();
			String sql = null;
			sql = "SELECT concertname FROM " + CONCERTTABLE + " WHERE concertname = '" + concertname + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) // at least one result
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Update concert
	 * 
	 * @param concert
	 */
	public void updateConcert(Concert concert) {
		try {
			System.out.println("Updating concert " + concert.getConcertname() + " ...");
			stmt = conn.connect().createStatement();
			String sql = "UPDATE " + CONCERTTABLE + " SET concertname = '" + concert.getConcertname() + "' , date = '"
					+ concert.getDate() + "' , createdDate = '" + concert.getCreatedDate() + "' , description = '"
					+ concert.getDescription() + "' , completed = '" + concert.getCompleted() + "' , artist = '"
					+ concert.getArtistname() + "' WHERE concertname = '" + concert.getConcertname() + "'";
			stmt.executeUpdate(sql);
			System.out.println("User " + concert.getConcertname() + " updated...");
			conn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/*
	 * Delete table
	 * 
	 */
	public void deleteTable() {
		try {
			System.out.println("Deleting table " + CONCERTTABLE + " ...");
			stmt = conn.connect().createStatement();
			String sql = "DROP TABLE " + CONCERTTABLE;
			stmt.executeUpdate(sql);
			System.out.println("Table " + CONCERTTABLE + " deleted...");
			conn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}