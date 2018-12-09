package work;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import ui.AttendanceController;

public class ReadJSON {

	private static JsonElement jsonTree;
	private static JsonObject jsonObject;
	private String path;
	
	public String read(String path) throws FileNotFoundException {

		//Saves absolute path to the database
		setPath(path);

		if(path.equals(null)) {
			return "please select a student database.";
		}
		else {
			FileReader reader = new FileReader(path);
			JsonParser parser = new JsonParser();

			//Parsing JSON file to jsonTree.
			jsonTree = parser.parse(reader);

			//Adding item to jsonObject as long as it is a jsonObject.
			if(jsonTree.isJsonObject()) {
				jsonObject = jsonTree.getAsJsonObject();
			}

			return "loaded";
		}

		
	}

	//Counting the number of students in the database.
	public int getStudentCount() {
		return jsonObject.size();
	}
	
	//Getting the student's name from TextField ID.
	public String getName(String id) {
		JsonObject studentData = (JsonObject)jsonObject.get(id);
		return studentData.get("name").toString().replaceAll("\"", "");
	}

	//Returns whether student is present or not.
	public Boolean getStatus(String id) {
		JsonObject studentData = (JsonObject)jsonObject.get(id);
		return Boolean.parseBoolean(studentData.get("is_Present").toString().replaceAll("\"", ""));
	}


	public void appendStatus(String id) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		JsonObject studentData = (JsonObject)jsonObject.get(id);

		if(Boolean.parseBoolean(studentData.get("is_Present").toString().replaceAll("\"", ""))) {
			studentData.addProperty("is_Present", false);

		}
		else {
			studentData.addProperty("is_Present", true);
		}

		FileWriter write = new FileWriter(getPath());
		gson.toJson(jsonObject, write);
		write.flush();
		write.close();

	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}
