package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class HomeController {
	
	//Declaring all editable components of the UI
	@FXML private AnchorPane rootPane;
	@FXML private Button homeButton;
	@FXML private Button attendanceButton;
	@FXML private Button classroomButton;
	
	
	//When one of the following is pressed, it will change the scene.
	public void homeClicked(ActionEvent event) throws IOException {
		System.out.println("You're already here!");
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Home.fxml"));
		rootPane.getChildren().setAll(pane);
		
	}
	
	public void attendanceClicked(ActionEvent event) throws IOException {
		System.out.println("Loading Attendance...");
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Attendance.fxml"));
		rootPane.getChildren().setAll(pane);
		
	}
	
	public void classroomClicked() throws IOException {
		System.out.println("Loading Classroom...");
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Classroom.fxml"));
		rootPane.getChildren().setAll(pane);
	}

}
