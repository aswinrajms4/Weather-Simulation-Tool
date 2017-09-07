package com.tcs.weather.utils;

import com.tcs.weather.service.WeatherReportingService;
import com.tcs.weather.serviceImpl.WeatherReportingServiceImpl;

/**
 * Generating the instance for WeatherReportingService
 *
 */
public class WeatherReportServiceFactory {
	public static WeatherReportingService getInstance() {
		return new WeatherReportingServiceImpl();
	}
}
