package com.helper;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.entities.PlaceEntity;
import com.rovertherapist.activity.HomeActivity;

//import android.widget.Toast;

public class PlacesHelper implements IPlacesHelper {
	private static final String LOG_TAG = "GoogleMapHelper";

	// private static final String
	// URL="https://maps.googleapis.com/maps/api/place/radarsearch/json?location=48.859294,2.347589&radius=1000	&types=food|cafe&sensor=false&key=AddYourOwnKeyHere";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_RADAR = "/radarsearch";
	private static final String OUT_JSON = "/json";
	private static final String LOCATION = "?location=";
	private String longitude = "";
	private String latitude = "";
	private String TYPES = "";
	private String RADIUS = "500";
	private static final String API_KEY = AndroidConstants.BROWSER_API_KEY;

	IPlaceDetailsHelper pdetails = new PlaceDetailsHelper();

	public List<PlaceEntity> searchPlaces(String longitude, String latitude,
			String RADIUS, String TYPES) {
		List<PlaceEntity> resultList = null;
		List<PlaceEntity> localresultList = null;
		List<PlaceEntity> finalresultList = new ArrayList<PlaceEntity>();
		if (latitude == null || longitude == null
				|| latitude.equalsIgnoreCase("null")
				|| longitude.equalsIgnoreCase("null")) {
			return null;
		}
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			String paramTypes = TYPES;
			// if (paramTypes.contains(CategoryConstants.ATTRACTIONS)) {
			// paramTypes = paramTypes.replace(CategoryConstants.ATTRACTIONS,
			// "Attractions");
			// }
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_RADAR
					+ OUT_JSON + LOCATION + URLEncoder.encode(latitude, "utf8")
					+ "," + URLEncoder.encode(longitude, "utf8") + "&radius="
					+ URLEncoder.encode(RADIUS, "utf8") + "&types="
					+ URLEncoder.encode(TYPES, "utf8") + "&sensor=false&key="
					+ API_KEY);
			HashMap params = new HashMap();
			params.put("method", "getPlaces");
			params.put("types", paramTypes);
			params.put("distance", RADIUS);
			params.put("latitude", latitude);
			params.put("longitude", longitude);
			params.put("PLACES_URL", URLEncoder.encode(sb.toString()));
			System.out.println("Calling server for locations ...");

			String op = HttpView.createURL(params);
			System.out.println("URL" + op);
			op = op.replaceAll(" ", "%20");
			op = op.replaceAll("ajax.jsp", "ajaxPhone.jsp");
			System.out.println("URL1" + op);
			localresultList = (List<PlaceEntity>) HttpView
					.connect2ServerObject(op);
			if (localresultList != null && localresultList.size() > 0) {
				System.out.println("Got " + localresultList.size()
						+ " Local places  ...");

				finalresultList.addAll(localresultList);
			}

			System.out.println("Place search URL " + sb);

			/*
			 * sb.append("?sensor=false&key=" + API_KEY);
			 * sb.append("&components=country:in"); sb.append("&input=" +
			 * URLEncoder.encode(input, "utf8"));
			 */

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return finalresultList;
		} catch (Exception e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return finalresultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("results");
			String status = (String) jsonObj.get("status");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<PlaceEntity>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				PlaceEntity placeEntity = new PlaceEntity();
				JSONObject geometryJsonArray = predsJsonArray.getJSONObject(i)
						.getJSONObject("geometry");
				JSONObject locationJsonArray = geometryJsonArray
						.getJSONObject("location");
				placeEntity
						.setLatitude((Double) (locationJsonArray.get("lat")));
				placeEntity
						.setLongitude((Double) (locationJsonArray.get("lng")));

				placeEntity.setId((String) predsJsonArray.getJSONObject(i).get(
						"id"));
				placeEntity.setReference((String) predsJsonArray.getJSONObject(
						i).get("reference"));
				placeEntity.setDetailEntity(pdetails
						.searchPlacesDetails(placeEntity.getReference()));
				resultList.add(placeEntity);
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}
		if (resultList.size() > 0)
			finalresultList.addAll(resultList);
		double dlng = StringHelper.n2d(longitude);
		double dlat = StringHelper.n2d(latitude);
		if (finalresultList != null) {
			for (int i = finalresultList.size() - 1; i >= 0; i--) {
				try {
					if (finalresultList.get(i) == null) {
						finalresultList.remove(i);
					}
					PlaceEntity rowItem = finalresultList.get(i);
					double lat = rowItem.getLatitude();
					double lng = rowItem.getLongitude();
					rowItem.setDistance(0);
					double dis = HomeActivity.distance(lat, lng, dlat, dlng);
					long d = Math.round(dis);
					rowItem.setDistance((int) d);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		}
		class CustomComparator implements Comparator<PlaceEntity> {
			@Override
			public int compare(PlaceEntity o1, PlaceEntity o2) {
				return o1.getDistance() - o2.getDistance();
			}
		}
		Collections.sort(finalresultList, new CustomComparator());
		System.out
				.println("FInal List has " + finalresultList != null ? finalresultList
						.size() : "0");
		return finalresultList;
	}
}
