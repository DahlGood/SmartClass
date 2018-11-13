package ui;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import work.ReadJSON;

public class Display extends Application{
		
	public static void main(String[] args) throws FileNotFoundException {
		
		launch(args);
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//Loading initial scene. Home page.
		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("SmartClass");
		//LOGO
		//stage.getIcons().add(new Image(Display.class.getResourceAsStream(""))); 
		stage.setScene(scene);
		stage.show();
		
	}
			
}