package com.util;

//This gives location name complete address by taking latitude and longitude
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.helper.StringHelperNew;

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

	public static Double[] getLatlngOfAddress(String add) {
		final String TAG_RESULTS = "results";
		final String TAG_GEO = "geometry";
		final String TAG_LOCATION = "location";
		final String TAG_LAT = "lat";
		final String TAG_LNG = "lng";
		Double[] latlong = new Double[2];

		String url = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ URLEncoder.encode(add) + "&sensor=true";

		JSONArray ja = null;
		try {
			System.out.println("URL " + url);
			StringBuffer json = StringHelperNew.readURLContent(url);
			JSONObject jobj = new JSONObject(json.toString());

			ja = jobj.getJSONArray(TAG_RESULTS);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject c = ja.getJSONObject(i);

				JSONObject loc = c.optJSONObject(TAG_GEO).optJSONObject(
						TAG_LOCATION);

				latlong[0] = (Double) loc.get(TAG_LAT);

				latlong[1] = (Double) loc.get(TAG_LNG);
				System.out.println(">>>>>>>>>>>>geocoderlat" + latlong[0]
						+ "<<<<<<<<<<<<<<<<<geocoderlng<" + latlong[1]);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

		return latlong;

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
		System.out.println(getLatlngOfAddress("Pune")[0]);
		System.out.println(getLatlngOfAddress("Pune")[1]);
		String[] arr = ReverseGeocoder
				.getLocation("18.508490118886186,73.7911561131477");
		System.out.println("==================");
		System.out.println(arr[0]);
		System.out.println(arr[1]);

	}
}
// http://maps.google.com/maps/geo?q=Lane Number 11, Paud Rd, Pune, Maharashtra,
// India&output=csv&key=0xtBhGKK6UEzbpmDiaFXgxFGiycbXQBzOiQspYg