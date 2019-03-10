package com.helper;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;

;

public class TempratureHelper {
	public static void main(String[] args) {
		getWeatherData("18.5089776", "73.791714");

	}

	public static HashMap map = new HashMap();
	static {
		map.put("200", "thunderstorm with light rain #11d.png#Thunderstorm");
		map.put("201", "thunderstorm with rain #11d.png#Thunderstorm");
		map.put("202", "thunderstorm with heavy rain #11d.png#Thunderstorm");
		map.put("210", "light thunderstorm #11d.png#Thunderstorm");
		map.put("211", "thunderstorm #11d.png#Thunderstorm");
		map.put("212", "heavy thunderstorm #11d.png#Thunderstorm");
		map.put("221", "ragged thunderstorm #11d.png#Thunderstorm");
		map.put("230", "thunderstorm with light drizzle #11d.png#Thunderstorm");
		map.put("231", "thunderstorm with drizzle #11d.png#Thunderstorm");
		map.put("232", "thunderstorm with heavy drizzle #11d.png#Thunderstorm");
		map.put("300", "light intensity drizzle #09d.png#Drizzle");
		map.put("301", "drizzle #09d.png#Drizzle");
		map.put("302", "heavy intensity drizzle #09d.png#Drizzle");
		map.put("310", "light intensity drizzle rain #09d.png#Drizzle");
		map.put("311", "drizzle rain #09d.png#Drizzle");
		map.put("312", "heavy intensity drizzle rain #09d.png#Drizzle");
		map.put("313", "shower rain and drizzle #09d.png#Drizzle");
		map.put("314", "heavy shower rain and drizzle #09d.png#Drizzle");
		map.put("321", "shower drizzle #09d.png#Drizzle");
		map.put("500", "light rain #10d.png#Rain");
		map.put("501", "moderate rain #10d.png#Rain");
		map.put("502", "heavy intensity rain #10d.png#Rain");
		map.put("503", "very heavy rain #10d.png#Rain");
		map.put("504", "extreme rain #10d.png#Rain");
		map.put("511", "freezing rain #13d.png#Rain");
		map.put("520", "light intensity shower rain #09d.png#Rain");
		map.put("521", "shower rain #09d.png#Rain");
		map.put("522", "heavy intensity shower rain #09d.png#Rain");
		map.put("531", "ragged shower rain #09d.png#Rain");
		map.put("600", "light snow #13d.png#Snow");
		map.put("601", "snow #13d.png#Snow");
		map.put("602", "heavy snow #13d.png#Snow");
		map.put("611", "sleet #13d.png#Snow");
		map.put("612", "shower sleet #13d.png#Snow");
		map.put("615", "light rain and snow #13d.png#Snow");
		map.put("616", "rain and snow #13d.png#Snow");
		map.put("620", "light shower snow #13d.png#Snow");
		map.put("621", "shower snow #13d.png#Snow");
		map.put("622", "heavy shower snow #13d.png#Snow");
		map.put("701", "mist #50d.png#Atmosphere");
		map.put("711", "smoke #50d.png#Atmosphere");
		map.put("721", "haze #50d.png#Atmosphere");
		map.put("731", "sand, dust whirls #50d.png#Atmosphere");
		map.put("741", "fog #50d.png#Atmosphere");
		map.put("751", "sand #50d.png#Atmosphere");
		map.put("761", "dust #50d.png#Atmosphere");
		map.put("762", "volcanic ash #50d.png#Atmosphere");
		map.put("771", "squalls #50d.png#Atmosphere");
		map.put("781", "tornado #50d.png#Atmosphere");
		map.put("800", "clear sky #01d.png#Clouds");
		map.put("801", "few clouds #02d.png #Clouds");
		map.put("802", "scattered clouds #03d.png#Clouds");
		map.put("803", "broken clouds #04d.png #Clouds");
		map.put("804", "overcast clouds #04d.png#Clouds");
		map.put("900", "tornado#NA#Extreme");
		map.put("901", "tropical storm#NA#Extreme");
		map.put("902", "hurricane#NA#Extreme");
		map.put("903", "cold#NA#Extreme");
		map.put("904", "hot#NA#Extreme");
		map.put("905", "windy#NA#Extreme");
		map.put("906", "hail#NA#Extreme");
		map.put("951", "calm#NA#Additional");
		map.put("952", "light breeze#NA#Additional");
		map.put("953", "gentle breeze#NA#Additional");
		map.put("954", "moderate breeze#NA#Additional");
		map.put("955", "fresh breeze#NA#Additional");
		map.put("956", "strong breeze#NA#Additional");
		map.put("957", "high wind, near gale#NA#Additional");
		map.put("958", "gale#NA#Additional");
		map.put("959", "severe gale#NA#Additional");
		map.put("960", "storm#NA#Additional");
		map.put("961", "violent storm#NA#Additional");
		map.put("962", "hurricane #NA#Additional");
		
	}

	// http://openweathermap.org/weather-conditions
	public static HashMap getWeatherData(String lat, String lng) {
		String q = "http://api.openweathermap.org/data/2.5/weather?lat="
				+ lat.trim() + "&lon=" + lng.trim();
		StringBuffer json = StringHelper.readURLContent(q);
		HashMap hm = new HashMap();
		try {
			JSONObject jobj = new JSONObject(json.toString());
			JSONObject temp = (JSONObject) jobj.get("main");
			JSONArray weather = (JSONArray) jobj.get("weather");
			System.out.println(weather.length());

			JSONObject we = (JSONObject) weather.get(0);
			System.out.println(we.get("id"));
			System.out.println(we.get("main"));
			System.out.println(we.get("description"));
			System.out.println(StringHelper.n2d(temp.get("temp")));
			double temp2 = StringHelper.n2d(temp.get("temp"));
			temp2 = temp2 - 273;
			hm.put("temp", temp2);
			hm.put("id", we.get("id"));
			hm.put("main", we.get("main"));
			hm.put("description", we.get("description"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hm;
	}
}
