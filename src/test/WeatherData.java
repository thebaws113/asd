package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WeatherData extends abstractWeather {
	private float humidity, presure;
	private MainData main;
	private String name;
	 private WindData wind;
	WeatherData(JsonElement e) {
		main = new MainData(e);
		this.name = e.getAsJsonObject().get("name").toString();
		 wind = new WindData(e);
	}
 	static public class WindData extends abstractWeather.Wind{
		private float speed;
		 
		WindData (JsonElement e){
			JsonObject mainS = (JsonObject) e.getAsJsonObject().get("wind").getAsJsonObject();
			this.speed = mainS.get("speed").getAsFloat();
			 
		}
		@Override
		public float getSpeed() {
			return this.speed;
		}

		@Override
		public int getDeg() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
 
	static public class MainData extends abstractWeather.Main {

		private float temp;
		private float tempMin;
		private float tempMax;
		private float pressure;
		private float humidity;

		MainData(JsonElement e) {
			JsonObject mainS = (JsonObject) e.getAsJsonObject().get("main").getAsJsonObject();
			this.temp = (float) (mainS.get("temp").getAsFloat() - 273.5);
			this.humidity = mainS.get("humidity").getAsFloat();
			this.pressure = mainS.get("pressure").getAsFloat();
			this.tempMax = mainS.get("temp_max").getAsFloat();
			this.tempMin = mainS.get("temp_min").getAsFloat();
		}

		@Override
		public float getTemp() {
			return temp;

		}

		@Override
		public float getTempMin() {
			// TODO Auto-generated method stub
			return tempMin;
		}

		@Override
		public float getTempMax() {
			// TODO Auto-generated method stub
			return tempMax;
		}

		@Override
		public float getPressure() {
			// TODO Auto-generated method stub
			return pressure;
		}

		@Override
		public float getHumidity() {
			// TODO Auto-generated method stub
			return humidity;
		}

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getTemp() {
		return this.main.temp;
	}

	@Override
	public float getHumidity() {
		return this.main.humidity;
	}

	@Override
	public float getPressure() {
		// TODO Auto-generated method stub
		return this.main.pressure;
	}

	@Override
	public float getWindSpeed() {
		// TODO Auto-generated method stub
		return this.wind.speed;
	}

	@Override
	public float getWindGust() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWindDeg() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPrecipitation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
