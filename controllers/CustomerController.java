package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

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

import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.AnchorPane;
import models.ConcertModel;
import models.UserConcertModel;
import resources.User;
import javafx.scene.control.TableColumn;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         CustomerController class
 *
 */
public class CustomerController implements Initializable {
	@FXML
	private Label lblError;
	@FXML
	private TableView<Map<String, String>> tableUsers;
	@FXML
	private TableColumn<String, String> columnConcert;
	@FXML
	private TableColumn<String, String> columnArtist;
	@FXML
	private TableColumn<String, String> columnDate;
	@FXML
	private TableColumn<String, String> columnCompleted;
	@FXML
	private TableColumn<String, String> columnDescription;
	@FXML
	private Button delete;
	@FXML
	private Button searchOthers;

	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		CustomerController.user = user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTable();
	}

	private void updateTable() {
		try {
			ResultSet rs1 = new UserConcertModel().retrieveConcerts(user.getUsername());

			ObservableList<Map<String, String>> concerts = FXCollections.observableArrayList();
			while (rs1.next()) {
				ResultSet rs = new ConcertModel().retrieveConcert(rs1.getString("concertname"));
				while (rs.next()) {
					Map<String, String> concert = new HashMap<>();
					concert.put("concertname", rs.getString("concertname"));
					concert.put("date", rs.getDate("date").toString());
					concert.put("artist", rs.getString("artist"));
					if (rs.getBoolean("completed"))
						concert.put("completed", "YES");
					else
						concert.put("completed", "NO");
					concert.put("description", rs.getString("description"));
					concerts.add(concert);
				}
			}

			columnConcert.setCellValueFactory(new MapValueFactory("concertname"));
			columnArtist.setCellValueFactory(new MapValueFactory("artist"));
			columnDate.setCellValueFactory(new MapValueFactory("date"));
			columnCompleted.setCellValueFactory(new MapValueFactory("completed"));
			columnDescription.setCellValueFactory(new MapValueFactory("description"));
			tableUsers.setItems(concerts);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#delete].onAction
	@FXML
	public void delete(ActionEvent event) {
		if (tableUsers.getSelectionModel().getSelectedItem() == null) {
			lblError.setText("No user selected");
			return;
		}
		String concertname = tableUsers.getSelectionModel().getSelectedItem().get("concertname");
		new UserConcertModel().deleteConcertUser(concertname, user.getUsername());
		lblError.setText(concertname + " added to favoutites!");
		updateTable();
	}

	// Event Listener on Button.onAction
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

	// Event Listener on Button[#searchOthers].onAction
	@FXML
	public void search(ActionEvent event) {
		try {
			SearchConcertsController.setUser(user);
			Main.stage.setTitle("Seacrh Concert View");
			Scene scene = new Scene(
					(AnchorPane) FXMLLoader.load(getClass().getResource("/views/SearchConcertsView.fxml")));
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
