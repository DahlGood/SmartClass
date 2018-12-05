package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.FileChooser;
import work.ReadJSON;

public class AttendanceController {

	@FXML private AnchorPane rootPane;
	@FXML private Button startButton;
	@FXML private Button load;
	@FXML private Button clear;
	@FXML private GridPane grid;
	@FXML private Label status;
	private String studentPath = "null";
	ArrayList<TextField> seat = new ArrayList<TextField>(5);

	public void loadStudents(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters();
		File f = fc.showOpenDialog(null);

		if(f != null) {
			studentPath = f.getAbsolutePath();
		}

		System.out.println(studentPath);

		
	}
	
	public void startAttendance()  throws FileNotFoundException {

		System.out.print("Start Button Pressed.");

		ReadJSON read = new ReadJSON();

		//Starts to read student database.
		status.setText(read.read(studentPath));

		//Initializing data for iterations.
		int count = read.getStudentCount();
		int row = 0;
		int nameBox = 1;
		for(int i = 0; i < read.getStudentCount(); i++) {

			DropShadow absent = new DropShadow();
			absent.setColor(Color.RED);
			absent.setOffsetX(0f);
			absent.setOffsetY(0f);

			DropShadow present = new DropShadow();
			present.setColor(Color.GREEN);
			present.setOffsetX(0f);
			present.setOffsetY(0f);

			DropShadow late = new DropShadow();
			late.setColor(Color.YELLOW);
			late.setOffsetX(0f);
			late.setOffsetY(0f);

			//Adds a new TextField to each student.
			seat.add(new TextField());

			//Sets students name to each TextField
			seat.get(i).setText(read.getName(Integer.toString(nameBox)));
			seat.get(i).setStyle("-fx-background-color: #282e36; -fx-text-fill: #e3e3e3; -fx-alignment: center;");


			System.out.println(read.getStatus(Integer.toString(nameBox)));

			if(read.getStatus(Integer.toString(nameBox))) {
				seat.get(i).setEffect(present);
			}
			else if(!read.getStatus(Integer.toString(nameBox))) {
				seat.get(i).setEffect(absent);
			}

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
	
	public void clearStudents() {
		
		//Clears grid.
		grid.getChildren().clear();
		
	}
	
}
