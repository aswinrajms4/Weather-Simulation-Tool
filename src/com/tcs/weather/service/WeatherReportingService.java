package com.tcs.weather.service;

import java.util.ArrayList;
import com.tcs.weather.common.WeatherData;
import com.tcs.weather.exception.WeatherSimulationException;

public interface WeatherReportingService {
	public ArrayList<WeatherData> getWeather() throws WeatherSimulationException;
	public void generateWeatherInfo(ArrayList<WeatherData> arrayListWeatherData);
}
