package test;

 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

 

public class Client {

	public static void main(String[] args) throws IOException {
Client c=new Client();
	}
	
	Client() throws IOException{
		 URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=London,uk");
	      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	      BufferedWriter writer = new BufferedWriter(new FileWriter("data.html"));
	      String line;
	      while ((line = reader.readLine()) != null) {
	    	  
	         System.out.println(line);
	         writer.write(line);
	         writer.newLine();
	      }
	      reader.close();
	      writer.close();
	   	
	

}
}