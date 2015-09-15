package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Client {
	static String baseUrl = "http://api.openweathermap.org/data/2.5/";

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		String a= "weather?q=London";
		c.query(a);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(c.query(a));
		System.out.print(element.toString());
	}
	
	public String query (String s) throws IOException {
		URL url = new URL(baseUrl+s);
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		String str = "" ;
		String line;
		while ((line = reader.readLine()) != null) {
			str = str + line;
		}
		
		reader.close();
		return str;
	}
}