package com.tcs.weather.handler;

import java.util.ArrayList;

import com.tcs.weather.common.WeatherData;
import com.tcs.weather.exception.WeatherSimulationException;
import com.tcs.weather.service.WeatherReportingService;
import com.tcs.weather.utils.WeatherReportServiceFactory;

public class WeatherSimulatorHandler {

	public static void main(String[] args) {
		try {
			WeatherSimulatorHandler weathersimulation = new WeatherSimulatorHandler();
			weathersimulation.process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void process() throws WeatherSimulationException {
		// Fetches the weather information for different locations
		WeatherReportingService weatherReportingService = WeatherReportServiceFactory
				.getInstance();

		ArrayList<WeatherData> weatherInfo = weatherReportingService
				.getWeather();
		weatherReportingService.generateWeatherInfo(weatherInfo);
	}

}
