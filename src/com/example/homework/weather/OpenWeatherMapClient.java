package com.example.homework.weather;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OpenWeatherMapClient implements WeatherDataSource {
	// the first part of the link from which we obtain the json response
	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

	/**
	 * gets the current weather condition based on location adds to the BASE_URL
	 * the "weather" string for specifing which kind of information we want and
	 * sends the longitude and latitude parameters Performs a GETRequest and
	 * obtains a JSON Response
	 */
	@Override
	public void getCurrentCondition(Location location,
			final Callback<WeatherCondition> callback) {
		AsyncHttpClient httpClient = new AsyncHttpClient();

		RequestParams params = new RequestParams();
		params.add("lat", String.valueOf(location.getLatitude()));
		params.add("lon", String.valueOf(location.getLongitude()));
		params.add("units", "metric");

		httpClient.get(String.format("%s/%s", BASE_URL, "weather"), params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode,
							org.apache.http.Header[] headers,
							org.json.JSONObject response) {
						callback.onSuccess(parseWeatherJson(response));
					}

					@Override
					public void onFailure(int statusCode,
							org.apache.http.Header[] headers,
							java.lang.Throwable throwable,
							org.json.JSONObject errorResponse) {
						callback.onError();
					}
				});
	}

	@Override
	public void getForecast(Location location,
			Callback<List<WeatherCondition>> callback) {
		throw new UnsupportedOperationException();
	}

	/** this is where the parsing of the JSON Object is made */
	private WeatherCondition parseWeatherJson(JSONObject object) {
		WeatherCondition condition = new WeatherCondition();

		try {
			condition.setCity(object.getString("name"));

			JSONObject weatherObj = object.getJSONArray("weather")
					.getJSONObject(0);
			condition.setType(getConditionType(weatherObj.getString("icon")));

			JSONObject mainObject = object.getJSONObject("main");

			condition.setTemp(mainObject.getDouble("temp"));
			condition.setTempMin(mainObject.getDouble("temp_min"));
			condition.setTempMax(mainObject.getDouble("temp_max"));

			condition.setDate(new Date(object.getLong("dt") * 1000));

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

		return condition;
	}

	/** this is for setting the type of the condition for chosing a pic later */
	private WeatherCondition.ConditionType getConditionType(String iconName) {
		if (iconName.startsWith("01")) {
			return WeatherCondition.ConditionType.CLEAR;
		} else if (iconName.startsWith("02")) {
			return WeatherCondition.ConditionType.FEW_CLOUDS;
		} else if (iconName.startsWith("03") || iconName.startsWith("04")) {
			return WeatherCondition.ConditionType.CLOUDS;
		} else if (iconName.startsWith("09")) {
			return WeatherCondition.ConditionType.SHOWER;
		} else if (iconName.startsWith("10")) {
			return WeatherCondition.ConditionType.RAIN;
		} else if (iconName.startsWith("11")) {
			return WeatherCondition.ConditionType.STORM;
		} else if (iconName.startsWith("13")) {
			return WeatherCondition.ConditionType.SNOW;
		} else if (iconName.startsWith("50")) {
			return WeatherCondition.ConditionType.MIST;
		}

		return WeatherCondition.ConditionType.CLEAR;
	}
}
