package controllers;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.LoginModel;
import models.UserModel;
import resources.User;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         LoginController class
 *
 */
public class LoginController {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label lblError;

	private LoginModel model;

	static User user = null;

	public LoginController() {
		model = new LoginModel();
	}

	public void login() {
		lblError.setText("");
		String username = this.txtUsername.getText();
		String password = this.txtPassword.getText();

		// Validations
		if (username == null || username.trim().equals("")) {
			lblError.setText("Username cannot be empty or spaces");
			return;
		}
		if (password == null || password.trim().equals("")) {
			lblError.setText("Password cannot be empty or spaces");
			return;
		}
		if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
			lblError.setText("Username / password cannot be empty or spaces");
			return;
		}

		// authentication check
		checkCredentials(username, password);

	}

	public void checkCredentials(String username, String password) {
		if (!model.getCredentials(username, password)) {
			lblError.setText("User or password incorrect.");
			return;
		}
		selectView(username);
	}

	public void selectView(String username) {
		try {
			AnchorPane root;
			LoginController.setUser(new UserModel().getUser(username));
			if (model.isArtist()) {
				// If user is artist, inflate artist view
				ArtistController.setUser(LoginController.getUser());
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ArtistView.fxml"));
				Main.stage.setTitle("Artist View");

			} else if (model.isManager()) {
				// If user is manager, inflate manager view
				ManagerController.setUser(LoginController.getUser());
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ManagerView.fxml"));
				Main.stage.setTitle("Manager View");
			} else {
				// If user is customer, inflate customer view
				CustomerController.setUser(LoginController.getUser());
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/CustomerView.fxml"));
				Main.stage.setTitle("Customer View");
			}

			Scene scene = new Scene(root);
			Main.stage.setScene(scene);

		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		LoginController.user = user;
	}

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