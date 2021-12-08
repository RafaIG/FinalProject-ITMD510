package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.UserModel;
import resources.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         RegisterController class
 *
 */
public class RegisterController implements Initializable {

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label lblError;
	@FXML
	private TextField txtCountry;
	@FXML
	private PasswordField txtPassword1;
	@FXML
	private TextField txtEmail;
	@FXML
	private ChoiceBox<String> accountType;

	private UserModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		accountType.getItems().add("Artist");
		accountType.getItems().add("Manager");
		accountType.getItems().add("Customer");
	}

	public RegisterController() {
		model = new UserModel();
	}

	@FXML
	public void register(ActionEvent event) {

		lblError.setText("");

		String username = this.txtUsername.getText();
		String country = this.txtCountry.getText();
		String email = this.txtEmail.getText();
		String accountType = this.accountType.getValue();
		String password = this.txtPassword.getText();
		String password1 = this.txtPassword1.getText();

		if (username == null || username.trim().equals("")) {
			lblError.setText("Username cannot be empty or spaces");
			return;
		}
		if (email == null || email.trim().equals("")) {
			lblError.setText("Email cannot be empty or spaces");
			return;
		}
		if (country == null || country.trim().equals("")) {
			lblError.setText("Country cannot be empty or spaces");
			return;
		}
		if (accountType == null || accountType.trim().equals("")) {
			lblError.setText("Need to select an account type");
			return;
		}
		if (password == null || password.trim().equals("") || password1 == null || password1.trim().equals("")) {
			lblError.setText("Password cannot be empty or spaces");
			return;
		}
		if (!password.trim().equals(password1.trim())) {
			lblError.setText("Password cannot be empty or spaces");
			return;
		}

		if (model.checkEmailExits(email)) {
			lblError.setText("email already exits.");
			return;
		}
		if (model.checkUsernameExits(username)) {
			lblError.setText("Username already exits.");
			return;
		}

		User user = new User(username, password, email, country, accountType.contentEquals("Artist") ? 1 : 0,
				accountType.contentEquals("Manager") ? 1 : 0);
		System.out.println(user);
		model.insertUser(user);
		lblError.setText("User created!");

		Main.stage.setTitle("User created View");
		try {
			UserCreatedController.setUser(user);
			UserCreatedController.setPassword(password);
			Main.stage.setScene(
					new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/UserCreatedView.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
