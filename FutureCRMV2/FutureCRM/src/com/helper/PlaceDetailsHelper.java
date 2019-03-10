package com.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.entities.DayEntity;
import com.entities.PlaceDetailEntity;
import com.entities.ReviewEntity;

public class PlaceDetailsHelper implements IPlaceDetailsHelper {

	private static final String LOG_TAG = "GoogleMapHelper";

	// private static final String
	// URL="https://maps.googleapis.com/maps/api/place/details/json?reference=CoQBcwAAADHt0Focc-rP2G35L-ZBzYB3nt3iDE8gFCPcvgGiezJiWaHZnTekpjp5Y9K6mIX18A4rGi32UidzJVwhDSZdUpHwaRccmDfFsdKXN75zCoh_78F0kZgpZhxdlcoS2aMGY49aSlpyGd5Sq8PG9GUfrqz_-4LipM2hwpLxg_nbvduCEhCIIQ7S2_B0ctnNJRffkiPQGhQRnd3laum5hBCCuJD9UFkZLftxtw&sensor=true&key=AIzaSyBqyR0lJFYYOyhgZ0GnSARWmCi4XTvcx4I";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_DETAILS = "/details";
	private static final String OUT_JSON = "/json";
	private static final String REF = "?reference=";
	private String longitude = "";
	private String latitude = "";
	private String TYPES = "";
	private String RADIUS = "500";
	private static final String API_KEY = AndroidConstants.BROWSER_API_KEY;

	public PlaceDetailEntity searchPlacesDetails(String ref) {

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_DETAILS
					+ OUT_JSON + REF + URLEncoder.encode(ref, "utf8")
					+ "&sensor=true&key=" + API_KEY);
			System.out.println("Place Details search URL " + sb);
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
			return null;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return null;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONObject predsJsonArray = (JSONObject) jsonObj
					.getJSONObject("result");

			// Extract the Place descriptions from the results

			PlaceDetailEntity placeDetail = new PlaceDetailEntity();
			/*
			 * JSONObject geometryJsonArray =
			 * predsJsonArray.getJSONObject(i).getJSONObject("geometry");
			 * JSONObject locationJsonArray =
			 * geometryJsonArray.getJSONObject("location");
			 * placeEntity.setLatitude((Double)(locationJsonArray.get("lat")));
			 * placeEntity.setLongitude((Double)(locationJsonArray.get("lng")));
			 * placeEntity.setId((String)
			 * predsJsonArray.getJSONObject(i).get("id"));
			 * placeEntity.setReference((String)
			 * predsJsonArray.getJSONObject(i).get("reference"));
			 */

			placeDetail.setFormatted_address((String) getJSONObject(
					predsJsonArray, "formatted_address"));
			placeDetail.setName((String) getJSONObject(predsJsonArray, "name"));
			placeDetail.setType(((JSONArray) getJSONObject(predsJsonArray,
					"types")).toString());
			placeDetail.setFormatted_phone_number((String) getJSONObject(
					predsJsonArray, "formatted_phone_number"));

			placeDetail.setUser_ratings((Integer) getJSONObject(predsJsonArray,
					"user_ratings_total"));

			placeDetail.setUrl((String) getJSONObject(predsJsonArray, "url"));

			JSONObject opening_hoursJsonObject = (JSONObject) getJSONObject(
					predsJsonArray, "opening_hours");
			if (opening_hoursJsonObject != null) {
				placeDetail.setOpen_now(opening_hoursJsonObject
						.getBoolean("open_now"));
				JSONArray periodsJsonArray = opening_hoursJsonObject
						.getJSONArray("periods");
				if (periodsJsonArray != null) {
					DayEntity[] opening_HRS = new DayEntity[periodsJsonArray
							.length()];

					for (int j = 0; j < periodsJsonArray.length(); j++) {
						try {
							DayEntity dayEntity = new DayEntity();
							JSONObject closeJsonArray = periodsJsonArray
									.getJSONObject(j).getJSONObject("close");
							JSONObject openJsonArray = periodsJsonArray
									.getJSONObject(j).getJSONObject("open");
							dayEntity.setDay(closeJsonArray.getInt("day"));
							dayEntity.setOpenHRS(openJsonArray.getInt("time"));
							dayEntity
									.setCloseHRS(closeJsonArray.getInt("time"));
							opening_HRS[j] = dayEntity;
							/*
							 * System.out.println(" H-----Day------"+dayEntity.
							 * getDay());
							 * System.out.println(" H-----Open Time------"
							 * +dayEntity.getOpenHRS());
							 * System.out.println(" H-----Close Time------"
							 * +dayEntity.getCloseHRS());
							 */
						} catch (Exception e) {

						}

					}

					placeDetail.setOpening_HRS(opening_HRS);
				}
			}

			// / Reviews

			JSONArray reviewsJsonArray = getJSONArray(predsJsonArray, "reviews");

			if (reviewsJsonArray != null) {
				ReviewEntity[] reviewEntities = new ReviewEntity[reviewsJsonArray
						.length()];
				for (int j = 0; j < reviewEntities.length; j++) {
					ReviewEntity reviewEntity = new ReviewEntity();
					// JSONObject review =
					// reviewsJsonArray.getJSONObject(j).getJSONObject("aspects");
					reviewEntity.setAuthorName(reviewsJsonArray
							.getJSONObject(j).getString("author_name"));
					reviewEntity.setText(reviewsJsonArray.getJSONObject(j)
							.getString("text"));
					reviewEntities[j] = reviewEntity;
				}

				placeDetail.setReviews(reviewEntities);
			}

			return placeDetail;

		} catch (Exception e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return null;
	}

	private Object getJSONObject(JSONObject jsonObject, String name) {
		try {
			return jsonObject.get(name);
		} catch (JSONException e) {
			return null;
		}
	}

	private JSONArray getJSONArray(JSONObject jsonObject, String name) {
		try {
			return jsonObject.getJSONArray(name);
		} catch (JSONException e) {
			return null;
		}

	}

}
