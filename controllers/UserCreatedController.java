package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import resources.User;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         UserCreatedController class
 *
 */
public class UserCreatedController {

	@FXML
	private Label lblError;

	private static User user;
	private static String password;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		UserCreatedController.user = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		UserCreatedController.password = password;
	}

	// Event Listener on Button.onAction
	@FXML
	public void login(ActionEvent event) {
		LoginController l = new LoginController();
		l.checkCredentials(user.getUsername(), password);
	}

	// Event Listener on Button.onAction
	@FXML
	public void register() {
		Main.stage.setTitle("Register View");
		try {
			Main.stage.setScene(
					new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/RegisterView.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
