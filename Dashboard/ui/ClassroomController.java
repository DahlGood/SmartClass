package ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ClassroomController {
	
	@FXML private AnchorPane rootPane;
	
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
