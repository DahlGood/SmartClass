package ui;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Display extends Application{
		
	public static void main(String[] args) throws FileNotFoundException {
		
		launch(args);
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		//Loading initial scene. Home page.
		Parent root = FXMLLoader.load(getClass().getResource("Attendance.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("SmartClass");
		//LOGO
		stage.getIcons().add(new Image(Display.class.getResourceAsStream("assets/logo.png")));
		stage.setScene(scene);
		stage.show();
		
	}
			
}