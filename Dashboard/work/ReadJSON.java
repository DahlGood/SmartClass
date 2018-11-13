package work;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class ReadJSON {

	private static JsonElement jsonTree;
	private static JsonObject jsonObject;
	private static JsonObject obj;
	private static JsonObject obj2;
	private static JsonArray jsonArray;
	
	public static void read() throws FileNotFoundException {
		
		FileReader reader = new FileReader("C:\\Users\\luked\\Dropbox\\Eclipse\\SmartClass\\src\\work\\class.json");
		
		JsonParser parser = new JsonParser();
		
		jsonTree = parser.parse(reader);
		
		if(jsonTree.isJsonObject()) {
			jsonObject = jsonTree.getAsJsonObject();
		}
		
		/*
		JsonObject f1 = (JsonObject) jsonObject.get("1004");
		JsonElement f2 = f1.get("name");
		System.out.println(f2);
		*/
		
	}
			
	
	
	public int getStudentCount() {
		return jsonObject.size();
	}
	
	public String getName(String id) {
		JsonObject studentData = (JsonObject)jsonObject.get(id);
		return studentData.get("name").toString().replaceAll("\"", "");
	}
	
}
