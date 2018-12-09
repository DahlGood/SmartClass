package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import work.ReadJSON;

public class AttendanceController {

	@FXML private AnchorPane rootPane;
	@FXML private Button startButton;
	@FXML private Button load;
	@FXML private Button clear;
	@FXML private Button loadClassroom;
	@FXML private GridPane grid;
	@FXML private Label status;
	private String studentPath = "null";
	private ArrayList<TextField> seat = new ArrayList<TextField>(5);
	private DropShadow absent = new DropShadow();
	private DropShadow present = new DropShadow();


	interface UpdateStatus {
		void setStatus(String nameBox);
	}

	public void classroom() throws IOException {

		System.out.println("Opening classroom.");
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Classroom.fxml"));
		rootPane.getChildren().setAll(pane);

	}

	public void loadStudents(ActionEvent event) {

		//Opens file browser
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters();
		File f = fc.showOpenDialog(null);

		//If a file is selected, store it's absolute path in studentPath
		if(f != null) {
			studentPath = f.getAbsolutePath();
		}

	}
	
	public void startAttendance()  throws FileNotFoundException {

		System.out.println("Displaying students");

		//Clears grid in case of database updates (Ex. changing student status)
		grid.getChildren().clear();

		//Runs ReadJSON
		ReadJSON read = new ReadJSON();

		//Starts to read student database and updates the status.
		status.setText(read.read(studentPath));

		//Initializing data for iterations.
		int count = read.getStudentCount();
		int row = 0;
		int nameBox = 1;

		//
		for(int i = 0; i < read.getStudentCount(); i++) {
			//Setting style info for absent & present students.
			absent.setColor(Color.RED);
			absent.setOffsetX(0f);
			absent.setOffsetY(0f);
			present.setColor(Color.GREEN);
			present.setOffsetX(0f);
			present.setOffsetY(0f);

			//Creates a new TextField for each student.
			seat.add(new TextField());

			//Assigns a students name to each TextField & sets style.
			seat.get(i).setText(read.getName(Integer.toString(nameBox)));
			seat.get(i).setStyle("-fx-background-color: #282e36; -fx-text-fill: #e3e3e3; -fx-alignment: center;");
			seat.get(i).setEditable(false);

			//Changes students status when professor clicks their name.
			int finalNameBox = nameBox;
			seat.get(i).setOnMouseClicked(event -> {
				try {
					status.setText("Click \"Start\" to view your changes.");
					read.appendStatus(Integer.toString(finalNameBox));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			//Gives a students TextField an effect depending on their status.
			if(read.getStatus(Integer.toString(nameBox))) {
				seat.get(i).setEffect(present);
			}
			else if(!read.getStatus(Integer.toString(nameBox))) {
				seat.get(i).setEffect(absent);
			}

			//Organizing each TextField into the appropriate cell.
			if(i < 7) {
				grid.add(seat.get(i), 0, row);
			}
			else if(i >= 7 && i < 14) {
				row = 0;
				grid.add(seat.get(i), 1, row);
			}
			else if(i > 14) {
				row = 0;
				grid.add(seat.get(i), 2, row);
			}

			count--;
			row++;
			nameBox++;
		}
		
	}
	
	public void clearStudents() {
		//Clears grid.
		grid.getChildren().clear();
		
	}
	
}
