package models;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import resources.Hash;
import resources.User;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         UserModel class
 *
 */
public class UserModel extends DBConnect {

	private static final String USERTABLE = "livefy_users";

	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;
	ResultSet result = null;

	// Constructor
	public UserModel() { // create db object instance
		conn = new DBConnect();
	}

	/**
	 * Create table for users
	 */
	public void createUserTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create LivefyUser Table...");
			System.out.println("Connected database successfully...");

			stmt = conn.connect().createStatement();

			// Execute create query
			System.out.println("Creating LivefyUser table in given database...");

			String sql = "CREATE TABLE IF NOT EXISTS " + USERTABLE + "(username VARCHAR(30) not null, "
					+ " email VARCHAR(60) not null, " + " password NVARCHAR(64), " + "country VARCHAR(30), "
					+ "artist BOOLEAN DEFAULT false, " + "manager BOOLEAN DEFAULT false, " + " UNIQUE(username,email), "
					+ "PRIMARY KEY(username))";
			stmt.executeUpdate(sql);
			System.out.println("Created LivefyUser table in given database...");
			conn.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	/**
	 * Retrieve all users information
	 * 
	 * @return
	 */
	public ResultSet retrieveUsers() {
		ResultSet rs = null;
		try {
			System.out.println("Retrieving all users information...");
			stmt = conn.connect().createStatement();
			String sql = "SELECT username, password, email, country, artist, manager FROM " + USERTABLE
					+ " order BY username ASC";
			rs = stmt.executeQuery(sql);
			System.out.println("Users retrieved...");
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Retrieve an specific user information
	 * 
	 * @return
	 */
	public User getUser(String username) {
		ResultSet rs = null;
		User u = null;
		try {
			System.out.println("Retrieving all users information...");
			stmt = conn.connect().createStatement();
			String sql = "SELECT username, password, email, country, artist, manager FROM " + USERTABLE
					+ " WHERE username = '" + username + "'";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				try {
					u = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
							rs.getString("country"), rs.getBoolean("artist") ? 1 : 0, rs.getBoolean("manager") ? 1 : 0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Users retrieved...");
			conn.connect().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	/**
	 * Add new user
	 * 
	 * @param user
	 */
	public void insertUser(User user) {
		try {
			System.out.println("Inserting user " + user.getUsername() + " into the table...");
			stmt = conn.connect().createStatement();
			String sql = null;

			sql = "INSERT INTO " + USERTABLE + "(username, email, password, country, artist, manager) VALUES ('"
					+ user.getUsername() + "', '" + user.getEmail() + "', '" + user.getPassword() + "', '"
					+ user.getCountry() + "', '" + user.getArtist() + "', '" + user.getManager() + "' )";

			stmt.executeUpdate(sql);

			System.out.println("User " + user.getUsername() + " inserted into LivefyUser.");
			conn.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	/**
	 * Check if user is login. Return true if credentials are valid
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkLoginUser(String username, String pass) {
		String password = null;
		try {
			password = Hash.createHashes(pass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Authorising user " + username + "...");
			stmt = conn.connect().createStatement();
			String sql = null;
			sql = "SELECT username, password FROM " + USERTABLE + " WHERE username = '" + username
					+ "' AND password = '" + password + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) // at least one result
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Check if email already exits. Return true if it exits
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkEmailExits(String email) {
		try {
			System.out.println("Checking email " + email + "...");
			stmt = conn.connect().createStatement();
			String sql = null;
			sql = "SELECT email FROM " + USERTABLE + " WHERE email = '" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) // at least one result
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Check if username already exits. Return true if it exits
	 * 
	 * @param username
	 * @return
	 */
	public boolean checkUsernameExits(String username) {
		try {
			System.out.println("Checking username " + username + "...");
			stmt = conn.connect().createStatement();
			String sql = null;
			sql = "SELECT username FROM " + USERTABLE + " WHERE username = '" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) // at least one result
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Delete user
	 * 
	 * @param email
	 */
	public void deleteUser(String username) {
		try {
			System.out.println("Deleteing user with username " + username + " ...");
			stmt = conn.connect().createStatement();
			String sql = "DELETE FROM " + USERTABLE + " where username = '" + username + "'";
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("User deleted...");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Update user
	 * 
	 * @param user
	 */
	public void updateUser(User user) {
		try {
			System.out.println("Updating user " + user.getUsername() + " ...");
			stmt = conn.connect().createStatement();
			String sql = "UPDATE " + USERTABLE + " SET artist = '" + user.getArtist() + "' , manager = '"
					+ user.getManager() + "' , country = '" + user.getCountry() + "' , email = '" + user.getEmail()
					+ "' WHERE username = '" + user.getUsername() + "'";
			stmt.executeUpdate(sql);
			System.out.println("User " + user.getUsername() + " updated...");
			conn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/*
	 * 
	 */
	public void deleteTable() {
		try {
			System.out.println("Deleting table " + USERTABLE + " ...");
			stmt = conn.connect().createStatement();
			String sql = "DROP TABLE " + USERTABLE;
			stmt.executeUpdate(sql);
			System.out.println("Table " + USERTABLE + " deleted...");
			conn.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}