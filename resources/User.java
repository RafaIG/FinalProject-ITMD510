package resources;

import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         User class
 *
 */
public class User {

	private String username;
	private String password;
	private String email;
	private String country;
	private int artist;
	private int manager;

	public User(String username, String password, String email, String country, int artist, int manager) {
		super();
		this.username = username;
		try {
			this.password = Hash.createHashes(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.email = email;
		this.country = country;
		this.artist = artist;
		this.manager = manager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getArtist() {
		return artist;
	}

	public void setArtist(int artist) {
		this.artist = artist;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", country=" + country
				+ ", artist=" + artist + ", manager=" + manager + "]";
	}

}
