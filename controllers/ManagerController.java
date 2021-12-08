package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import models.UserModel;
import resources.User;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         ManagerController class
 *
 */
public class ManagerController implements Initializable {

	@FXML
	private TableView<Map<String, String>> tableUsers;
	@FXML
	private TableColumn<String, String> columnUser;
	@FXML
	private TableColumn<String, String> columnEmail;
	@FXML
	private TableColumn<String, String> columnCountry;
	@FXML
	private TableColumn<String, String> columnUserType;
	@FXML
	private Label lblError;

	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ManagerController.user = user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTable();
	}

	private void updateTable() {
		try {
			ResultSet rs = new UserModel().retrieveUsers();

			ObservableList<Map<String, String>> users = FXCollections.observableArrayList();
			while (rs.next()) {
				// aux.add(new User(rs.getString("username"), rs.getString("password"),
				// rs.getString("email"),
				// rs.getString("country"), rs.getBoolean("artist") ? 1 : 0,
				// rs.getBoolean("manager") ? 1 : 0));
				Map<String, String> user = new HashMap<>();
				user.put("username", rs.getString("username"));
				user.put("email", rs.getString("email"));
				user.put("country", rs.getString("country"));
				if (rs.getBoolean("artist"))
					user.put("userType", "artist");
				else if (rs.getBoolean("manager"))
					user.put("userType", "manager");
				else
					user.put("userType", "customer");
				users.add(user);
			}

			columnUser.setCellValueFactory(new MapValueFactory("username"));
			columnEmail.setCellValueFactory(new MapValueFactory("email"));
			columnCountry.setCellValueFactory(new MapValueFactory("country"));
			columnUserType.setCellValueFactory(new MapValueFactory("userType"));
			tableUsers.setItems(users);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void delete(ActionEvent event) {
		if (tableUsers.getSelectionModel().getSelectedItem() == null) {
			lblError.setText("No user selected");
			return;
		}

		String username = tableUsers.getSelectionModel().getSelectedItem().get("username");
		new UserModel().deleteUser(username);
		lblError.setText(username + " deleted!");
		updateTable();
	}

	// Event Listener on Button[#update].onAction
	@FXML
	public void update(ActionEvent event) {
		if (tableUsers.getSelectionModel().getSelectedItem() == null) {
			lblError.setText("No user selected");
			return;
		}

		try {
			String username = tableUsers.getSelectionModel().getSelectedItem().get("username");
			ManagerUpdateUserController.setUser(user);
			ManagerUpdateUserController.setUsername(username);
			Main.stage.setTitle("Update User View");
			Scene scene = new Scene(
					(AnchorPane) FXMLLoader.load(getClass().getResource("/views/ManagerUpdateUserView.fxml")));
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

}
