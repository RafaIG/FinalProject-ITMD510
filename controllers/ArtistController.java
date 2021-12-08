package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import resources.User;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         ArtistController class
 *
 */
public class ArtistController {
	@FXML
	private Label lblError;
	@FXML
	private Button createConcert;
	@FXML
	private Button delete2;
	@FXML
	private Button manageConcerts;

	static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ArtistController.user = user;
	}

	// Event Listener on Button[#createConcert].onAction
	@FXML
	public void createConcert(ActionEvent event) {
		try {
			CreateConcertController.setUser(user);
			Main.stage.setTitle("Create Concert View");
			Scene scene = new Scene(
					(AnchorPane) FXMLLoader.load(getClass().getResource("/views/CreateConcertView.fxml")));
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#delete2].onAction
	@FXML
	public void logout(ActionEvent event) {
		try {
			Main.stage.setTitle("Login View");
			Scene scene = new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml")));
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#delete11].onAction
	@FXML
	public void manageConcerts(ActionEvent event) {
		try {
			ConcertArtistController.setUser(user);
			Main.stage.setTitle("Concert View");
			Scene scene = new Scene(
					(AnchorPane) FXMLLoader.load(getClass().getResource("/views/ConcertArtistView.fxml")));
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
