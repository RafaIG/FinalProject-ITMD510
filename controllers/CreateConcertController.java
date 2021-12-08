package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.ConcertModel;
import resources.Concert;
import resources.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         CreateConcertController class
 *
 */
public class CreateConcertController {
	@FXML
	private TextField txtConcertname;
	@FXML
	private Label lblError;
	@FXML
	private TextField txtDescription;
	@FXML
	private DatePicker dateTable;

	private ConcertModel model = new ConcertModel();
	static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		CreateConcertController.user = user;
	}

	@FXML
	public void createConcert(ActionEvent event) {

		lblError.setText("");

		String concertname = this.txtConcertname.getText();
		Date date = Date.valueOf(this.dateTable.getValue());
		Timestamp createdDate = new java.sql.Timestamp(new java.util.Date().getTime());
		String description = this.txtDescription.getText();

		if (concertname == null || concertname.trim().equals("")) {
			lblError.setText("Concertname cannot be empty or spaces");
			return;
		}
		if (description == null || description.trim().equals(""))
			description = "-";
		if (date == null) {
			lblError.setText("Date cannot be empty or spaces");
			return;
		}

		if (model.checkConcertname(concertname)) {
			lblError.setText("Concertname already exits.");
			return;
		}

		Concert concert = new Concert(concertname, date, createdDate, description,
				(date.getTime() < createdDate.getTime()) ? 1 : 0, user.getUsername());
		model.insertConcert(concert);
		lblError.setText("Concert created!");

		Main.stage.setTitle("Artist View");
		try {
			ArtistController.setUser(user);
			Main.stage.setScene(
					new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/ArtistView.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void back(ActionEvent event) {
		try {
			Main.stage.setTitle("Artist View");
			ArtistController.setUser(user);
			Scene scene = new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/ArtistView.fxml")));
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
