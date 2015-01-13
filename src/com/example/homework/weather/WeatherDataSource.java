package com.example.homework.weather;

import java.util.List;

import android.location.Location;


public interface WeatherDataSource {
	public interface Callback<T> {
		void onSuccess(T arg);

		void onError();
	}

	void getCurrentCondition(Location location,
			Callback<WeatherCondition> callback);

	void getForecast(Location location,
			Callback<List<WeatherCondition>> callback);
}
