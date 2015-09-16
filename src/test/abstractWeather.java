package test;

import com.google.gson.JsonObject;

public abstract class abstractWeather {

	static abstract public class Main {

		abstract public float getTemp();

		abstract public float getTempMin();

		abstract public float getTempMax();

		abstract public float getPressure();

		abstract public float getHumidity();
	}

	static abstract public class Wind {

		abstract public float getSpeed();

		abstract public int getDeg();

	}
	abstract public String getName();
	
	abstract public float getTemp();

	abstract public float getHumidity();

	abstract public float getPressure();

	abstract public float getWindSpeed();

	abstract public float getWindGust();

	abstract public int getWindDeg();

	abstract public int getPrecipitation();

}