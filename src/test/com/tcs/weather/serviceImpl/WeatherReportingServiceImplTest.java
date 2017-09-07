package com.tcs.weather.serviceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.tcs.weather.common.WeatherData;
import com.tcs.weather.exception.WeatherSimulationException;

public class WeatherReportingServiceImplTest extends WeatherData {
	WeatherReportingServiceImpl weatherReportingService = new WeatherReportingServiceImpl();

	@Test
	public void testGetWeather()
			throws WeatherSimulationException {
		ArrayList<WeatherData>  arrayListWeatherData = weatherReportingService.getWeather();

		assertNotNull("Failure - Expected Weather Data that is not Null",
				arrayListWeatherData);
		assertTrue("Failure - The service method does not calculate Humidity.",
				(arrayListWeatherData.get(0).getHumidity() != 0));
		assertTrue("Failure - The service method does not calculate Pressure.",
				(arrayListWeatherData.get(0).getPressure() != 0));
		assertNotNull(
				"Failure - The service method does not calculate Temperature.",
				arrayListWeatherData.get(0).getTemperature());
		assertFalse(
				"Failure - The service method does not calculate Weather Condition.",
				arrayListWeatherData.get(0).getWeatherCondition().equals(""));
	}

	@Test
	public void testFetchPosition() throws FileNotFoundException, IOException,
			ParseException {
		ArrayList<WeatherData> arrayListWeatherData = weatherReportingService
				.fetchPosition();

		assertNotNull("Failure - position data is not Null",
				arrayListWeatherData);
		assertEquals("Failure - data for 10 cities.", 10,
				arrayListWeatherData.size());

	}

}
