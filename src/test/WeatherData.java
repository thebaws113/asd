package test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WeatherData extends abstractWeather {
	private float humidity, presure;
	 private MainData main;

	WeatherData(JsonElement e) {
		main =   new MainData(e);
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
			this.humidity =  mainS.get("humidity").getAsFloat();
			this.pressure = mainS.get("pressure").getAsFloat();
			this.tempMax =  mainS.get("temp_max").getAsFloat();
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
		return 0;
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
