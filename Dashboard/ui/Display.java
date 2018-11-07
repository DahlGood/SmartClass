package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Display extends Application{
		
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("SmartClass");
		//LOGO
		//stage.getIcons().add(new Image(Display.class.getResourceAsStream(""))); 
		stage.setScene(scene);
		stage.show();
	}
			
}