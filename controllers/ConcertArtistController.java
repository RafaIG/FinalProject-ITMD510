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
import resources.User;
import javafx.scene.control.TableColumn;

/**
 *
 * @author Rafael Ines Guillen rinesguillen@hawk.iit.edu A20474355
 * 
 *         Final Project
 * 
 *         ConcertArtistController class
 *
 */
public class ConcertArtistController implements Initializable {

	@FXML
	private Label lblError;
	@FXML
	private TableView<Map<String, String>> tableUsers;
	@FXML
	private TableColumn<String, String> columnConcert;
	@FXML
	private TableColumn<String, String> columnDateCreated;
	@FXML
	private TableColumn<String, String> columnDate;
	@FXML
	private TableColumn<String, String> columnCompleted;
	@FXML
	private TableColumn<String, String> columnDescription;
	@FXML
	private Button delete;
	@FXML
	private Button logout;

	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ConcertArtistController.user = user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateTable();
	}

	private void updateTable() {
		try {
			ResultSet rs = new ConcertModel().retrieveConcertsArtist(user.getUsername());

			ObservableList<Map<String, String>> concerts = FXCollections.observableArrayList();
			while (rs.next()) {
				// aux.add(new User(rs.getString("username"), rs.getString("password"),
				// rs.getString("email"),
				// rs.getString("country"), rs.getBoolean("artist") ? 1 : 0,
				// rs.getBoolean("manager") ? 1 : 0));
				Map<String, String> concert = new HashMap<>();
				concert.put("concertname", rs.getString("concertname"));
				concert.put("date", rs.getDate("date").toString());
				concert.put("createdDate", rs.getDate("createdDate").toString());
				if (rs.getBoolean("completed"))
					concert.put("completed", "YES");
				else
					concert.put("completed", "NO");
				concert.put("description", rs.getString("description"));
				concerts.add(concert);
			}

			columnConcert.setCellValueFactory(new MapValueFactory("concertname"));
			columnDateCreated.setCellValueFactory(new MapValueFactory("createdDate"));
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
			lblError.setText("No concert selected");
			return;
		}

		String concertname = tableUsers.getSelectionModel().getSelectedItem().get("concertname");
		new ConcertModel().deleteConcert(concertname);
		lblError.setText(concertname + " deleted!");
		updateTable();
	}

	// Event Listener on Button[#logout].onAction
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
