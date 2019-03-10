package com.helper;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

;

//for temperature that is according to the current weather
public class TempratureHelper {
	public static void main(String[] args) {
	HashMap map=	getWeatherData("18.5089776", "73.791714");
	System.out.println(map);
		
		 
	}
	
//http://openweathermap.org/weather-conditions
	public static HashMap getWeatherData(String lat, String lng) {
		String q = "http://api.openweathermap.org/data/2.5/weather?lat="
				+ lat.trim() + "&lon=" + lng.trim();
		StringBuffer json = StringHelperNew.readURLContent(q);
		HashMap hm=new HashMap();
		try {
			JSONObject jobj = new JSONObject(json.toString());
			JSONObject temp = (JSONObject) jobj.get("main");
			JSONArray weather = (JSONArray) jobj.get("weather");
			System.out.println(weather.length());

			JSONObject we = (JSONObject) weather.get(0);
			System.out.println(we.get("id"));
			System.out.println(we.get("main"));
			System.out.println(we.get("description"));
			System.out.println(StringHelperNew.n2d(temp.get("temp")));
			double temp2=StringHelperNew.n2d(temp.get("temp"));
			temp2=temp2-273;
			hm.put("temp",temp2 );
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
