package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         DBConnect class
 *
 */
public class DBConnect {

	private static String DB_URL = "jdbc:mysql://www.papademas.net:3307/510fp";
	private static String USER = "fp510";
	private static String PASS = "510";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}

}
