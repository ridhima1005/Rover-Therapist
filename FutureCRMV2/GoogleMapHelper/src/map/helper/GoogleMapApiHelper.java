package map.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoogleMapApiHelper {
	public void drawMarker(GPSModel node, GoogleMap map) {
		final LatLng source = new LatLng(node.getLat(), node.getLng());
		MarkerOptions mo = new MarkerOptions().position(source)
				.title(node.getTitle()).snippet(node.getDesc());
		if (node.icon != null) {
			mo.icon(BitmapDescriptorFactory.fromBitmap(node.getIcon()));
		}
		Marker hamburg = map.addMarker(mo);

		System.out.println(node.getTitle() + " " + node.getDesc());
	}

	public ArrayList drawPath(String result, GoogleMap map) {

		return drawRouteMap(result, map, 0);
	}

	public ArrayList drawRouteMap(String result, GoogleMap map, int routeIndex) {
		ArrayList arr = new ArrayList();
		try {
			// Tranform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");

			JSONObject routes = routeArray.getJSONObject(routeIndex);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);
			Random rnd = new Random();
			int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
					rnd.nextInt(256));
			for (int z = 0; z < list.size() - 1; z++) {

				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				Polyline line = map.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(color).geodesic(true));
			}

			JSONArray legs = (JSONArray) routes.get("legs");
			System.out.println();
			JSONObject steps = (JSONObject) legs.get(0);
			JSONArray routePoints = (JSONArray) steps.get("steps");

			// Add start point with total distance and time of travel
			try {
				RouteModel rm = new RouteModel();
				rm.setHtml_instructions(StringHelper.n2s(steps
						.get("start_address")));
				JSONObject duration = (JSONObject) steps.get("duration");
				rm.setDuration(StringHelper.n2s(duration.get("text")));
				duration = (JSONObject) steps.get("distance");
				rm.setDistancekm(StringHelper.n2s(duration.get("text")));
				rm.setDirections("start");
				arr.add(rm);
			} catch (Exception e) {
				// TODO: handle exception
			}

			for (int i = 0; i < routePoints.length(); i++) {
				JSONObject jo = (JSONObject) routePoints.get(i);
				RouteModel rm = new RouteModel();
				try {
					rm.setHtml_instructions(StringHelper.n2s(jo
							.get("html_instructions")));
					JSONObject duration = (JSONObject) jo.get("duration");
					rm.setDuration(StringHelper.n2s(duration.get("text")));
					duration = (JSONObject) jo.get("distance");
					rm.setDistancekm(StringHelper.n2s(duration.get("text")));
					rm.setDirections(StringHelper.n2s(jo.get("maneuver")));
				} catch (Exception e) {
					// TODO: handle exception
				}
				arr.add(rm);
			}
			try {
				RouteModel rm = new RouteModel();
				rm.setHtml_instructions(StringHelper.n2s(steps
						.get("end_address")));
				rm.setDistancekm("");
				rm.setDuration("");
				rm.setDirections("end");
				arr.add(rm);
			} catch (Exception e) {
				// TODO: handle exception
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

	public static List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	public static String getLocationCity(double lat, double lonng) {
		String location = "";
		JSONArray ja;
		String city = "";
		String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ lat + "," + lonng + "&sensor=true";
		StringBuffer json = FileHelper.readURLContent(url);
		try {
			System.out.println("URL " + url);

			JSONObject myjson = new JSONObject(json.toString());

			ja = (JSONArray) myjson.get("results");
			// JSONArray add= (JSONArray) ((JSONObject)
			// ja.get(0)).get("address_components");

			city = ((JSONObject) ja.get(0)).getString("formatted_address");
			// location=((JSONObject) ja.get(0)).get("formatted_address");
			System.out.println("......................................" + city);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return city;

	}

	public String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(sourcelat));
		urlString.append(",");
		urlString.append(Double.toString(sourcelog));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(destlat));
		urlString.append(",");
		urlString.append(Double.toString(destlog));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	public String makeURL(String sourcelat, String sourcelog, String destlat,
			String destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(sourcelat);
		urlString.append(",");
		urlString.append(sourcelog);
		urlString.append("&destination=");// to
		urlString.append(destlat);
		urlString.append(",");
		urlString.append(destlog);
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	private void getlatlnt(String frm, String too) {
		// TODO Auto-generated method stub
		HashMap mapData = new HashMap();
		mapData = MapGeocoder.getDistanceTimeDetails(frm, too);
		String[] list = (String[]) mapData.get("START_LATLNG");

		src_lat = StringHelper.safeObjToDouble(list[0]);
		src_long = StringHelper.safeObjToDouble(list[1]);
		String[] list2 = (String[]) mapData.get("END_LATLNG");

		dest_lat = StringHelper.safeObjToDouble(list2[0]);
		dest_long = StringHelper.safeObjToDouble(list2[1]);

		distancee = (String) mapData.get("DISTANCE");
		time = (String) mapData.get("TIME");

		city = MapGeocoder.getlocationCity(src_lat, src_long);
		System.out.println("i m here..................." + city);
		System.out.println("distance" + distancee + "time" + time + "src lat"
				+ src_lat);

	}

	public static String url = "", from = "", to = "", distancee, time, city;
	public static double src_lat, src_long, dest_lat, dest_long;
}
