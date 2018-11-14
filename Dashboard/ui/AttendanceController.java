package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import work.ReadJSON;

public class AttendanceController {

	@FXML private AnchorPane rootPane;
	@FXML private Button startButton;
	@FXML private Button load;
	@FXML private Button clear;
	@FXML private GridPane grid;
	ArrayList<TextField> seat = new ArrayList<TextField>(5);
	
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
	
	
	public void loadStudents(ActionEvent event) throws FileNotFoundException {
		System.out.print("Start Button Pressed.");
		
		ReadJSON read = new ReadJSON();
		
		//Starts to read student database.
		read.read();
		
		//Initializing data for iterations.
		int count = read.getStudentCount();
		int row = 0;
		int nameBox = 1;
		for(int i = 0; i < read.getStudentCount(); i++) {
			
			//Adds a new TextField to each student.
			seat.add(new TextField());
			//Sets students name to each TextField
			seat.get(i).setText(read.getName(Integer.toString(nameBox)));
			seat.get(i).setStyle("-fx-background-color: #282e36; -fx-text-fill: #e3e3e3; -fx-alignment: center;");
			
			//Organizing each TextField into the appropriate cell.
			if(count <= 7) {
				grid.add(seat.get(i), 0, row);
			}
			else if(count > 7 && count <= 14) {
				row = 0;
				grid.add(seat.get(i), 1, i);
			}
			else if(count > 14) {
				row = 0;
				grid.add(seat.get(i), 2, i);
			}
			
			count--;
			row++;
			nameBox++;
		}
		
	}
	
	public void startAttendance() {
		
		//RUN FACIAL RECOGNITION PYTHON HERE.
		
	}
	
	public void clearStudents() {
		
		//Clears grid.
		grid.getChildren().clear();
		
	}
	
}
