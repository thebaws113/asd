package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Client {
	static String baseUrl = "http://api.openweathermap.org/data/2.5/";

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		String a = "weather?q=Bucharest";
		c.query(a);
		JsonParser jsonParser = new JsonParser();
		JsonElement element = jsonParser.parse(c.query(a));

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(element);
		System.out.println(json);
		
		
		WeatherData data = new WeatherData(element );
		System.out.printf("%s %d C,humidity:  %.1f %% , pressure:  %.1f mb",data.getName(),(int)( data.getTemp()),data.getHumidity(),data.getPressure());
		
	}

	public String query(String location) {
		HttpURLConnection con = null;
		InputStream is = null;

		try {
			con = (HttpURLConnection) (new URL(baseUrl + location)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();

			// Let's read the response
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null)
				buffer.append(line + "\r\n");

			is.close();
			con.disconnect();
			return buffer.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}

		return null;

	}

}
