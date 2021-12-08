package application;

import models.ConcertModel;
import models.UserConcertModel;
import models.UserModel;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         Reset class
 *
 */
public class Reset {

	public static void main(String[] args) {

		UserConcertModel uc = new UserConcertModel();
		uc.deleteTable();
		ConcertModel m = new ConcertModel();
		m.deleteTable();
		UserModel u = new UserModel();
		u.deleteTable();

		u.createUserTable();
		m.createConcertTable();
		uc.createUserConcertTable();
	}
}
