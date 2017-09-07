package com.tcs.weather.service;

import com.tcs.weather.common.WeatherConstants;
import com.tcs.weather.common.WeatherData;

public class TemperatureService {

	/**
	 * Method to calculate the temperature index based on latitude value.
	 * Assuming temperature to be minimum in Polar (MIN_LATITUDE) and maximum at
	 * the equator (MAX_LATITUDE) Temperature index is used to calculate the
	 * temperature.
	 * 
	 * @param latitude
	 * @return
	 */
	public double getLatitudeTemperatureIndex(double latitude) {
		latitude = Math.abs(latitude);
		return 1- (latitude-WeatherConstants.MIN_LATITUDE)/ (WeatherConstants.MAX_LATITUDE - WeatherConstants.MIN_LATITUDE);
	}

	/**
	 * Method to calculate the temperature index based on elevation value.
	 * Assuming temperature is minimum at earth's top-most point (MAX_ELEVATION)
	 * and maximum at the sea level (MIN_ELEVATION)
	 * 
	 * @param elevation
	 * @return
	 */
	public double getElevationTemperatureIndex(double elevation) {
		return 1- (elevation-WeatherConstants.MIN_ELEVATION)/ (WeatherConstants.MAX_ELEVATION - WeatherConstants.MIN_ELEVATION);
	}

	/**
	 * Calculate the temperature from the index values calculated from Latitude
	 * and Elevation. An average of the two index is found and is multiplied
	 * with the global maximum temperature to get the temperature value.
	 * 
	 * @param currentDateTime
	 * @return
	 */
	public double getTemperature(WeatherData weatherData) {
		double latitudeIndex = getLatitudeTemperatureIndex(weatherData
				.getLatitude());
		double elevationIndex = getElevationTemperatureIndex(weatherData
				.getElevation());
		double tempIndex = (latitudeIndex + elevationIndex) / 2;
		return (WeatherConstants.MAX_GLOBAL_TEMPERATURE * tempIndex);
	}
}
