package com.helper;

//gives location name complete address by taking latitude and longitude
import java.io.BufferedReader;
//import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;

public class ReverseGeocoder {
	private final static String ENCODING = "UTF-8";
	private final static String KEY = "xyz";

	public static class Location {
		public String lon, lat;

		private Location(String lat, String lon) {
			this.lon = lon;
			this.lat = lat;
		}

		public String toString() {
			return "Lat: " + lat + ", Lon: " + lon;
		}
	}

	public static String[] getLocation(String address) {
		String addresspincode[] = new String[2];
		String add = " ";
		String pincode = " ";
		String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ address + "&sensor=true";
		try {
			if (address.equalsIgnoreCase("0.0,0.0")) {
				add = "";
				addresspincode[0] = add;
				addresspincode[1] = pincode;
				return addresspincode;
			}
			System.out.println("URL " + url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new URL(url).openStream()));
			String line;
			StringBuffer json = new StringBuffer();
			Location location = null;
			int statusCode = -1;
			while ((line = in.readLine()) != null)
				json.append(line);
			JSONArray ja;

			JSONObject myjson = new JSONObject(json.toString());
			// ja = new JSONArray(json.toString());
			ja = (JSONArray) myjson.get("results");
			// myjson = new JSONObject(json.toString());
			myjson.get("results");
			Object last = ((JSONObject) ja.get(0)).get("formatted_address");

			JSONArray jarray = (JSONArray) (((JSONObject) ja.get(0))
					.get("address_components"));
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject jo = jarray.getJSONObject(i);

				JSONArray types = (JSONArray) jo.get("types");
				for (int j = 0; j < types.length(); j++) {
					if (types.getString(0).equalsIgnoreCase("postal_code")) {
						pincode = jo.get("long_name").toString();
						break;
					}

				}

			}
			System.out.println(last);
			System.out.println("pincode " + pincode);

			add = last.toString();
			System.out.println("Final adrress is---" + add);
			addresspincode[0] = add;
			addresspincode[1] = pincode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addresspincode;
	}

	public static void main(String[] argv) throws Exception {
		String[] arr = ReverseGeocoder.getLocation("18.4992907,73.7938519");
		System.out.println("==================");
		System.out.println("piyuccccccccccccccccc" + arr[0]);
		;
		System.out.println(arr[1]);
		;
	}
}