package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
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
 *         SearchConcertsController class
 *
 */
public class SearchConcertsController implements Initializable {
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
	private Button addFavourite;

	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		SearchConcertsController.user = user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTable();
	}

	private void updateTable() {
		try {
			ResultSet rs = new UserConcertModel().retrieveConcerts(user.getUsername());
			ArrayList<String> listConcerts = new ArrayList<String>();
			while (rs.next()) {
				listConcerts.add(rs.getString("concertname"));
			}

			ObservableList<Map<String, String>> concerts = FXCollections.observableArrayList();
			rs = new ConcertModel().retrieveConcerts();
			while (rs.next()) {
				if (!listConcerts.contains(rs.getString("concertname"))) {
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

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#addFavourite].onAction
	@FXML
	public void addFavourite(ActionEvent event) {
		if (tableUsers.getSelectionModel().getSelectedItem() == null) {
			lblError.setText("No user selected");
			return;
		}
		String concertname = tableUsers.getSelectionModel().getSelectedItem().get("concertname");
		new UserConcertModel().insertConcertUser(concertname, user.getUsername());
		lblError.setText(concertname + " added to favoutites!");
		updateTable();
	}

	// Event Listener on Button.onAction
	@FXML
	public void back(ActionEvent event) {
		try {
			Main.stage.setTitle("Customer View");
			CustomerController.setUser(user);
			Scene scene = new Scene((AnchorPane) FXMLLoader.load(getClass().getResource("/views/CustomerView.fxml")));
			Main.stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
