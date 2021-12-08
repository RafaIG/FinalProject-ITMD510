package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import models.ConcertModel;
import models.UserConcertModel;
import models.UserModel;
import resources.Concert;
import resources.User;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         Initialize class
 *
 */
public class Initialize {

	public static void main(String[] args) {

		UserModel u = new UserModel();
		u.createUserTable();

		ConcertModel m = new ConcertModel();
		m.createConcertTable();

		UserConcertModel uc = new UserConcertModel();
		uc.createUserConcertTable();

		User user = new User("manager", "qwerty", "manager@gmail.com", "Canada", 0, 1);
		u.insertUser(user);

		user = new User("charles", "123", "cArles@gmail.com", "USA", 0, 0);
		u.insertUser(user);
		user = new User("jhon", "123", "jhon@gmail.com", "USA", 0, 0);
		u.insertUser(user);
		user = new User("maria", "qwerty", "wder@gmail.com", "Russia", 0, 0);
		u.insertUser(user);
		user = new User("sophia", "qwerty", "bulg@gmail.com", "USA", 0, 0);
		u.insertUser(user);
		user = new User("hans", "gfbgr", "flame@gmail.com", "Germany", 0, 0);
		u.insertUser(user);
		user = new User("luigi", "dvfd", "lui@gmail.com", "Italy", 0, 0);
		u.insertUser(user);
		user = new User("johnny", "6678", "j@gmail.com", "Canada", 0, 0);
		u.insertUser(user);
		user = new User("tom", "qwerty", "tommy@gmail.com", "USA", 0, 0);
		u.insertUser(user);
		user = new User("nata", "qwerty", "nata@gmail.com", "Spain", 0, 0);
		u.insertUser(user);
		user = new User("taylor", "qwerty", "taylor@gmail.com", "India", 0, 0);
		u.insertUser(user);
		user = new User("cris", "cristina", "c@gmail.com", "England", 0, 0);
		u.insertUser(user);

		user = new User("coldplay", "111", "coldplay@gmail.com", "USA", 1, 0);
		u.insertUser(user);
		user = new User("morat", "qwerty", "morat@gmail.com", "USA", 1, 0);
		u.insertUser(user);
		user = new User("queen", "qwerty", "fredy@gmail.com", "England", 1, 0);
		u.insertUser(user);
		user = new User("FinalProject", "123456", "final@gmail.com", "USA", 1, 0);
		u.insertUser(user);

		Concert c = new Concert("First Test", new java.sql.Date(new java.sql.Date(670315971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "Test to create a concert.", 1, "queen");
		m.insertConcert(c);
		c = new Concert("Bohemia", new java.sql.Date(new java.sql.Date(1670315971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "Yeah.", 1, "queen");
		m.insertConcert(c);
		c = new Concert("USA Travel", new java.sql.Date(new java.sql.Date(1670215971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "Up up up.", 1, "queen");
		m.insertConcert(c);
		c = new Concert("Olimpicgames", new java.sql.Date(new java.sql.Date(1570315971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "-", 1, "queen");
		m.insertConcert(c);

		c = new Concert("Clocks", new java.sql.Date(new java.sql.Date(1670915981).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "Clocks.", 1, "coldplay");
		m.insertConcert(c);
		c = new Concert("Britsh Travel", new java.sql.Date(new java.sql.Date(1676215971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()),
				"Coldplay are a British rock band formed in London in 1996.", 1, "coldplay");
		m.insertConcert(c);
		c = new Concert("BCN2589", new java.sql.Date(new java.sql.Date(1540315971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "oldplay are a British rock.", 1, "coldplay");
		m.insertConcert(c);

		c = new Concert("Vamos a Volver", new java.sql.Date(new java.sql.Date(1680315971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "Concert!!!!", 1, "morat");
		m.insertConcert(c);
		c = new Concert("Como te atreves", new java.sql.Date(new java.sql.Date(1680315971).getTime()),
				new java.sql.Timestamp(new java.util.Date().getTime()), "First time in Spain!", 1, "morat");
		m.insertConcert(c);

		// Main.retrieveUsers();
	}

	public static void retrieveUsers() {
		UserModel u = new UserModel();
		ResultSet rs = u.retrieveUsers();
		try {
			while (rs.next()) {
				try {
					User aux = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),
							rs.getString("country"), rs.getBoolean("artist") ? 1 : 0, rs.getBoolean("manager") ? 1 : 0);
					System.out.println(aux.toString());
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
