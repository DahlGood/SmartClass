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

public class AttendanceController {

	@FXML private AnchorPane rootPane;
	private ArrayList<TextField> seat = new ArrayList<TextField>();
	private Button startButton;
	private GridPane grid;
	
	
	
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
	
	
	public void currentStatus(ActionEvent event) {
		
		System.out.print("Start Button Pressed.");
		
		seat.add(new TextField("Name"));
		System.out.println(seat);
		System.out.println(seat.get(0));
		grid.add(seat.get(0), 0, 0);
		
		
	}
	
}
