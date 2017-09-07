package com.tcs.weather.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tcs.weather.common.WeatherConstants;
import com.tcs.weather.common.WeatherData;
import com.tcs.weather.exception.WeatherSimulationException;
import com.tcs.weather.service.TemperatureService;
import com.tcs.weather.service.WeatherReportingService;

public class WeatherReportingServiceImpl implements WeatherReportingService {
	/**
	 * Service method to get weather data 1)Calls fetchPosition() to fetch the
	 * locations , their positions and elevations from the location.txt file
	 * 2)From the position data, loops through the arrayList of Locations ; For
	 * each location , calculate the humidity, temperature and pressure and find
	 * the weather condition.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Override
	public ArrayList<WeatherData> getWeather()
			throws WeatherSimulationException {

		ArrayList<WeatherData> arrayListWeatherData = null;
		try {
			arrayListWeatherData = fetchPosition();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		arrayListWeatherData = calculateWeather(arrayListWeatherData);
		generateWeatherInfo(arrayListWeatherData);

		return arrayListWeatherData;
	}

	/**
	 * This method gets the locations and their latitude , longitude and
	 * elevation data from the location file.
	 * 
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public ArrayList<WeatherData> fetchPosition() throws FileNotFoundException,
			IOException, ParseException {
		ArrayList<WeatherData> arrayListWeatherData = new ArrayList<WeatherData>();
		File locationFile = new File(System.getProperty("user.dir")
				+ "\\resources\\locations.txt");

		JSONParser jsonParser = new JSONParser();

		JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(
				locationFile));

		for (Object object : jsonArray) {
			WeatherData weatherData = new WeatherData();
			JSONObject jsonObject = (JSONObject) object;

			weatherData.setLocation((String) jsonObject.get("location"));
			JSONArray position = (JSONArray) jsonObject.get("position");
			weatherData.setLatitude((Double) position.get(0));

			weatherData.setLongitude((Double) position.get(1));

			weatherData.setElevation((Long) position.get(2));

			arrayListWeatherData.add(weatherData);

		}
		return arrayListWeatherData;
	}

	/**
	 * This method calculates the weather parameters. It calculates - Relative
	 * Humidity, Pressure, Temperature, WeatherCondition
	 * 
	 * @param arrayListWeatherData
	 * @param currentDateTime
	 * @return
	 */
	public ArrayList<WeatherData> calculateWeather(
			ArrayList<WeatherData> arrayListWeatherData) {
		for (WeatherData weatherData : arrayListWeatherData) {
			TemperatureService temperatureService = new TemperatureService();
			weatherData.setTemperature(Math.round(temperatureService
					.getTemperature(weatherData)));
			weatherData.setPressure(Math.round(getPressure(
					weatherData.getElevation(), weatherData.getTemperature())));
			weatherData.setHumidity(Math.round(getHumidity(
					weatherData.getPressure(), weatherData.getTemperature())));

			weatherData.setWeatherCondition(getConditions(weatherData
					.getTemperature()));

		}
		return arrayListWeatherData;

	}

	/**
	 * This method gets the current time in ISO8601 date format and data would
	 * be fetched for the 10 cities
	 * 
	 * @param arrayListWeatherData
	 * @param currentDateTime
	 */
	@Override
	public void generateWeatherInfo(ArrayList<WeatherData> arrayListWeatherData) {
		if (null != arrayListWeatherData && arrayListWeatherData.size() > 0) {
			for (WeatherData weatherData : arrayListWeatherData) {
				String strCurrentDateTime = getISODate(weatherData
						.getLongitude());
				System.out.println(String.format(WeatherConstants.OUTPUT_DATA,
						weatherData.getLocation(), weatherData.getLatitude(),
						weatherData.getLongitude(), weatherData.getElevation(),
						strCurrentDateTime, weatherData.getWeatherCondition(),
						weatherData.getTemperature(),
						weatherData.getPressure(), weatherData.getHumidity()));

			}
		}
	}

	/**
	 * Method to generate Humidity at the given location
	 * 
	 * Absolute humidity is defined as the mass of water vapour in a certain
	 * volume. If ideal gas behaviour is assumed the absolute humidity can be
	 * calculated using A = C · Pw/T (g/m3) , where C = Constant 2.16679 gK/J Pw
	 * = Vapour pressure in Pa T = Temperature in K
	 * 
	 * @param elevation
	 * @return
	 */
	private int getHumidity(double pressure, double temperature) {
		Double humidity = new Double(
				WeatherConstants.HUM_CONSTANT
						* ((pressure * 2) / (WeatherConstants.CELSIUS_CONVERTER + temperature)));
		return humidity.intValue();
	}

	/**
	 * This method generates the pressure in hPa
	 * P = SeaLevel Pressure * power((1- 0.0065h/ T+0.0065h + 273.15), 5.257)
	 * @param elev
	 * @param temp
	 * @return The calculated pressure in hPa
	 */
	private double getPressure(double elevation, double temperature) {

		double pressure = WeatherConstants.PRESSURE_SEA_LEVEL
				* Math.pow(
						(1 - ((0.0065 * elevation) / (temperature
								+ (0.0065 * elevation) + WeatherConstants.CELSIUS_CONVERTER))),
						WeatherConstants.PRESSURE_POWER);
		return pressure;
	}
	/**
	 * This method to determine weather condition based on the temperature.
	 * 
	 * @param currentDateTime
	 * @return
	 */
	private String getConditions(double temperature) {

		if (temperature <= 0) {
			return "Snow";
		} else if (temperature <= WeatherConstants.TEMP_RAIN_MAX
				&& temperature > WeatherConstants.TEMP_RAIN_MIN) {
			return "Rain";
		} else {
			return "Sunny";
		}
	}

	/**
	 * This method gets the current time in ISO8601 date format
	 * 
	 * @param currentDateTime
	 * @return
	 */
	public String getISODate(double longitude) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
		Calendar currentDateTime = Calendar.getInstance(TimeZone.getDefault());
		currentDateTime.setTime(new Date());
		int timeinMin = 0;
		timeinMin = (int) ((longitude * WeatherConstants.LONG_CONV_MIN) / WeatherConstants.CONV_TO_HOURS);
		currentDateTime.add(Calendar.HOUR_OF_DAY, timeinMin);
		Date date = currentDateTime.getTime();
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
				.format(date);
		return formattedDate;
	}

}
