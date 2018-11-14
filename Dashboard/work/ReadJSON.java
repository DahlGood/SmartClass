package work;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReadJSON {

	private static JsonElement jsonTree;
	private static JsonObject jsonObject;
	
	public static void read() throws FileNotFoundException {
		
		//Temporary absolute file location
		FileReader reader = new FileReader("C:\\Users\\luked\\Dropbox\\Eclipse\\SmartClass\\src\\work\\class.json");
		JsonParser parser = new JsonParser();
		
		//Parsing JSON file to jsonTree.
		jsonTree = parser.parse(reader);
		
		//Adding item to jsonObject as long as it is a jsonObject.
		if(jsonTree.isJsonObject()) {
			jsonObject = jsonTree.getAsJsonObject();
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
	
	public boolean getStatus(String id) {
		JsonObject studentData = (JsonObject)jsonObject.get(id);
		return Boolean.parseBoolean(studentData.get("is_Present").toString().replaceAll("\"", ""));
	}
	
}
