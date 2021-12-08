package models;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import resources.Hash;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         LoginModel class
 *
 */
public class LoginModel extends DBConnect {

	private boolean artist;
	private boolean manager;
	private int pid;

	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;
	ResultSet result = null;

	// Constructor
	public LoginModel() { // create db object instance
		conn = new DBConnect();
	}

	public Boolean getCredentials(String username, String password) {
		String pass = null;
		try {
			pass = Hash.createHashes(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String query = "SELECT * FROM livefy_users WHERE username = ? and password = ?;";
		try (PreparedStatement stmt = conn.connect().prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				setArtist(rs.getBoolean("artist"));
				setManager(rs.getBoolean("manager"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isArtist() {
		return artist;
	}

	public void setArtist(boolean artist) {
		this.artist = artist;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public int getId() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

}// end class