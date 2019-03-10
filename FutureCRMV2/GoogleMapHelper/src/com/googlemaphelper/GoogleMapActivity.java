package com.googlemaphelper;

import static map.helper.StringHelper.n2d;
import static map.helper.StringHelper.n2s;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import map.helper.FileHelper;
import map.helper.GPSModel;
import map.helper.GoogleMapApiHelper;
import map.helper.MapGeocoder;
import map.helper.StringHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.listpopulate.CustomRoadListViewAdapter;

public class GoogleMapActivity extends FragmentActivity {
	public ListView roadDirectionsListView;
	public TextView roadmapTitle;
	public Button showAlternateRouteBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initMap();
		roadDirectionsListView = (ListView) findViewById(R.id.listView1);
		roadmapTitle = (TextView) findViewById(R.id.roadmapTitle);
		showAlternateRouteBtn = (Button) findViewById(R.id.showAlternateRouteBtn);
	}

	public void addButton() {
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.googleMapRelativeLayout);
		Button bt = new Button(this);
		bt.setText("Set Profile");
		// bt.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fnButtonClick();
			}
		});
		relativeLayout.addView(bt);

	}

	public void fnButtonClick() {

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		distance = "";
		time = "";
		int type = getIntent().getIntExtra("TYPE", 0);
		toast("In Google Map Activity " + type);
		switch (type) {
		case 1: // Plot road map from src to dest address name

			boolean showDirections = getIntent().getBooleanExtra(
					"showDirections", false);
			String showDirectionsTitle = getIntent().getStringExtra(
					"showDirectionsTitle");
			if (showDirections) {
				roadDirectionsListView.setVisibility(ListView.VISIBLE);
				roadmapTitle.setText(showDirectionsTitle);
				showAlternateRouteBtn.setVisibility(ListView.VISIBLE);
				showAlternateRouteBtn
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								loadRouteInfo(jsonResult, map);
							}
						});
			} else {
				roadDirectionsListView.setVisibility(ListView.INVISIBLE);
				roadmapTitle.setVisibility(ListView.INVISIBLE);
				showAlternateRouteBtn.setVisibility(ListView.INVISIBLE);
			}

			String src = getIntent().getStringExtra("src");
			String src_title = getIntent().getStringExtra("src_title");
			if (src_title == null) {
				src_title = src;
			}
			String src_desc = n2s(getIntent().getStringExtra("src_desc"));
			// drawMarker(src_title, src_desc);
			Bitmap src_bitmap = null;
			if (getIntent().getParcelableExtra("src_icon") != null)
				src_bitmap = (Bitmap) getIntent()
						.getParcelableExtra("src_icon");

			String dest = getIntent().getStringExtra("dest");
			String dest_title = getIntent().getStringExtra("dest_title");
			if (dest_title == null) {
				dest_title = dest;
			}
			String dest_desc = n2s(getIntent().getStringExtra("dest_desc"));
			Bitmap dest_bitmap = null;
			if (getIntent().getParcelableExtra("dest_icon") != null)
				dest_bitmap = (Bitmap) getIntent().getParcelableExtra(
						"dest_icon");

			HashMap mapData = new HashMap();
			mapData = MapGeocoder.getDistanceTimeDetails(src, dest);
			String[] srcLatLong = (String[]) mapData.get("START_LATLNG");
			String[] destLatLong = (String[]) mapData.get("END_LATLNG");
			Bitmap bitmap11 = (Bitmap) getIntent().getParcelableExtra(
					"BitmapImage");
			distance = n2s(mapData.get("DISTANCE"));
			time = n2s(mapData.get("TIME"));
			GPSModel srcModel = new GPSModel(n2d(srcLatLong[0]),
					n2d(srcLatLong[1]), src, src_title, src_desc, src_bitmap);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					srcModel.getLat(), srcModel.getLng()), 15));

			GPSModel destModel = new GPSModel(n2d(destLatLong[0]),
					n2d(destLatLong[1]), dest, dest_title, dest_desc,
					dest_bitmap);
			String url = gmap.makeURL(srcLatLong[0], srcLatLong[1],
					destLatLong[0], destLatLong[1]);
			gmap.drawMarker(srcModel, map);
			gmap.drawMarker(destModel, map);
			toast("Draw Google Map Activity");
			DrawRoadMapOnGoogle con = new DrawRoadMapOnGoogle();
			System.out.println("in after connectAsys" + con);
			con.execute(new Object[] { url, map });

			break;
		case 2:// 2. Add Marker on Map
			String src_single = getIntent().getStringExtra("src");
			String src_title_single = getIntent().getStringExtra("src_title");
			if (src_title_single == null) {
				src_title_single = src_single;
			}
			String src_desc_single = n2s(getIntent().getStringExtra("src_desc"));
			Bitmap bitmap22 = null;
			if (getIntent().getParcelableExtra("single_icon") != null)
				bitmap22 = (Bitmap) getIntent().getParcelableExtra(
						"single_icon");

			Double[] latlong = MapGeocoder.getLatlngOfAddress(src_single);
			GPSModel single = new GPSModel(latlong[0], latlong[1], src_single,
					src_title_single, src_desc_single, bitmap22);
			try {
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						latlong[0], latlong[1]), 10));
			} catch (Exception e) {
				e.printStackTrace();
			}
			gmap.drawMarker(single, map);
			break;
		case 3:
			map.clear();
			ArrayList<GPSModel> multi_marker = (ArrayList<GPSModel>) getIntent()
					.getSerializableExtra("multiMarker");
			for (Iterator iterator = multi_marker.iterator(); iterator
					.hasNext();) {
				GPSModel gpsModel = (GPSModel) iterator.next();
				String addresss = gpsModel.getAddress();
				String title = gpsModel.getTitle();

				Double[] latlong1 = MapGeocoder.getLatlngOfAddress(addresss);
				gpsModel.setAddress(addresss);
				gpsModel.setLat(latlong1[0].doubleValue());
				gpsModel.setLng(latlong1[1].doubleValue());
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						latlong1[0], latlong1[1]), 10));
				gmap.drawMarker(gpsModel, map);
			}

			break;
		case 4:

			multi_marker = (ArrayList<GPSModel>) getIntent()
					.getSerializableExtra("multiMarker");
			for (int i = 0; i < multi_marker.size(); i++) {
				GPSModel gpsModel = (GPSModel) multi_marker.get(i);
				GPSModel destGpsModel = null;
				if (i + 1 == multi_marker.size())
					destGpsModel = (GPSModel) multi_marker.get(0);
				else
					destGpsModel = (GPSModel) multi_marker.get(i + 1);
				String addresss = gpsModel.getAddress();
				String title = gpsModel.getTitle();
				// Double[] latlong1= MapGeocoder.getLatlngOfAddress(addresss);
				// gpsModel.setAddress(addresss);
				// gpsModel.setLat(latlong1[0].doubleValue());
				// gpsModel.setLng(latlong1[1].doubleValue());
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						gpsModel.getLat(), gpsModel.getLng()), 10));
				// gmap.drawPath(result, map)Marker(gpsModel, map);
				width = 4;
				// color=Color.RED;
				Polyline line = map.addPolyline(new PolylineOptions()
						.add(new LatLng(gpsModel.getLat(), gpsModel.getLng()),
								new LatLng(destGpsModel.getLat(), destGpsModel
										.getLng()))

						.width(width).color(color).geodesic(true));

				gmap.drawMarker(gpsModel, map);
				gmap.drawMarker(destGpsModel, map);
			}

			break;
		default:
			break;
		}

	}

	public class DrawRoadMapOnGoogle extends AsyncTask<Object, Void, String> {
		private ProgressDialog progressDialog;
		GoogleMap googleMap = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(GoogleMapActivity.this);
			progressDialog.setMessage("Fetching route, Please wait...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			String url = StringHelper.n2s(params[0]);
			googleMap = (GoogleMap) params[1];
			// String jason= getJSONFromUrl(url);
			String jason = FileHelper.readURLContent(url).toString();
			return jason;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (result != null) {
				System.out.println("in  onPostExecute");

				try {
					JSONObject json = new JSONObject(result);
					JSONArray routeArray = json.getJSONArray("routes");
					noOfRoutes = routeArray.length();
					currentIndex = 0;
					jsonResult = result;
					loadRouteInfo(jsonResult, googleMap);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.out.println("in  onPostExecute");
			progressDialog.hide();
		}
	}

	public void loadRouteInfo(String result, GoogleMap googleMap) {
		currentIndex++;
		if (currentIndex >= noOfRoutes) {
			currentIndex = 0;
		}
		System.out.println("Route Info " + currentIndex);
		ArrayList arr = gmap.drawRouteMap(result, googleMap, currentIndex);
		CustomRoadListViewAdapter ca = new CustomRoadListViewAdapter(
				GoogleMapActivity.this, android.R.layout.simple_list_item_1,
				arr);
		toast("Draw Google Map Activity " + arr.size());
		roadDirectionsListView.setAdapter(ca);
		roadDirectionsListView.bringToFront();
	}

	public int currentIndex = -1;
	public int noOfRoutes = 0;
	public String jsonResult = "";

	private void initMap() {
		SupportMapFragment mf = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment1);
		map = mf.getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		gmap = new GoogleMapApiHelper();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		if (getIntent().getBooleanExtra("SHOW_DISTANCE_TIME", false)) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
		}
		return true;
	}

	//fare, distance and time calculation
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent main = null;
		final int timeId = R.id.time;
		final int distanceId = R.id.distance;
		if (item.getItemId() == timeId) {
			showAlert(time, "Required Timew to Travel");
		} else if (item.getItemId() == distanceId) {
			showAlert(distance, "Required Distance to Travel");
		} else if (item.getItemId() == R.id.item1) {
			SharedPreferences s = PreferenceManager
					.getDefaultSharedPreferences(GoogleMapActivity.this);
			int rate = Integer.parseInt((s.getString("taxirates", "10")));
			int rate1km = Integer
					.parseInt((s.getString("taxiRates1stkm", "17")));
			double fare = 0;
			String distanceStr = distance.substring(0,
					distance.indexOf("km") - 1);
			double distanceInt = Double.parseDouble(distanceStr);
			if (distanceInt > 1) {
				fare = (distanceInt - 1) * rate;
				fare = fare + rate1km;
			} else {
				fare = rate1km;
			}

			showAlert(String.valueOf(fare) + " Rs. ", "Taxi Fare");
		}
		return true;

	}

	private void showAlert(String message, String title) {
		AlertDialog.Builder alert = new AlertDialog.Builder(
				GoogleMapActivity.this);
		alert.setTitle(title);
		alert.setCancelable(false);
		alert.setMessage(message);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		alert.show();
	}

	public void toast(String ms) {
		Toast.makeText(GoogleMapActivity.this, ms, 2000).show();
	}

	public GoogleMap map = null;
	public GoogleMapApiHelper gmap;
	private Marker customMarker;
	String time, distance;
	public int width, color;
}
