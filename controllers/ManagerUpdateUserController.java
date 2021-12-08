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

import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         ManagerUpdateUserController class
 *
 */
public class ManagerUpdateUserController implements Initializable {

	@FXML
	private Label lblError;
	@FXML
	private Label lblUsername;
	@FXML
	private TextField txtCountry;
	@FXML
	private TextField txtEmail;
	@FXML
	private ChoiceBox<String> accountType;

	static User user;
	static String username;
	private UserModel model;
	private String previousEmail;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ManagerUpdateUserController.user = user;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		ManagerUpdateUserController.username = username;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		accountType.getItems().add("Artist");
		accountType.getItems().add("Manager");
		accountType.getItems().add("Customer");

		User user = new UserModel().getUser(username);
		lblUsername.setText(username);
		this.txtCountry.setText(user.getCountry());
		this.txtEmail.setText(user.getEmail());
		this.previousEmail = user.getEmail();
		if (user.getManager() == 1)
			this.accountType.setValue("Manager");
		else if (user.getArtist() == 1)
			this.accountType.setValue("Artist");
		else
			this.accountType.setValue("Customer");

		model = new UserModel();
	}

	// Event Listener on Button.onAction
	@FXML
	public void update(ActionEvent event) {

		lblError.setText("");

		String country = this.txtCountry.getText();
		String email = this.txtEmail.getText();
		String accountType = this.accountType.getValue();

		if (email == null || email.trim().equals("")) {
			lblError.setText("Email cannot be empty or spaces");
			return;
		}
		if (country == null || country.trim().equals("")) {
			lblError.setText("Country cannot be empty or spaces");
			return;
		}
		if (accountType == null || accountType.trim().equals("")) {
			lblError.setText("AccountType cannot be empty or spaces");
			return;
		}

		if (!email.contentEquals(this.previousEmail) && model.checkEmailExits(email)) {
			lblError.setText("email already exits.");
			return;
		}

		User userUpdated = new User(username, " ", email, country, accountType.contentEquals("Artist") ? 1 : 0,
				accountType.contentEquals("Manager") ? 1 : 0);
		if (ManagerUpdateUserController.getUser().getUsername() == username)
			ManagerUpdateUserController.setUser(userUpdated);
		model.updateUser(userUpdated);
		lblError.setText("User updated!");
	}

	// Event Listener on Button.onAction
	@FXML
	public void back(ActionEvent event) {
		try {
			Main.stage.setTitle("Manager View");
			Scene scene = new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/ManagerView.fxml")));
			ManagerUpdateUserController.setUser(user);
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
