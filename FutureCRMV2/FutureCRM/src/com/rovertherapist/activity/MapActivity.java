package com.rovertherapist.activity;

import java.util.ArrayList;

import map.helper.GPSModel;
import map.helper.MapGeocoder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.googlemaphelper.GoogleMapActivity;

public class MapActivity extends GoogleMapActivity {
	protected int _splashTime = 3000; // time to display the splash screen in ms
	boolean _active = true;
	GPSTracker g = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		g = new GPSTracker(getApplicationContext());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if (g != null) {
				g.stopUsingGPS();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trackUser(String add, double lat, double longi) {

		try {
			BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(
					R.drawable.user);

			GPSModel single = new GPSModel(lat, longi, add, "Place Location",
					add, bd.getBitmap());

			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
					longi), 10));
			gmap.drawMarker(single, map);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	BitmapDrawable bd = null;
	String title = "";

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		GoogleMapActivity gm = new GoogleMapActivity();
		String slat = getIntent().getStringExtra("lat");

		if (slat != null && slat.length() > 0) {
			bd = (BitmapDrawable) getResources().getDrawable(R.drawable.high);
			width = 4;
			color = Color.RED;
			title = "Place location";
		}
		String slong = getIntent().getStringExtra("long");
		String add = getIntent().getStringExtra("add");
		if (slong != null && slong.length() > 0) {
			bd = (BitmapDrawable) getResources().getDrawable(R.drawable.home);
			width = 4;
			color = Color.GREEN;
			title = "Place location";

			ArrayList<GPSModel> a = new ArrayList<GPSModel>();

			String lat = slat;

			String lng = slong;

			Intent intent = getIntent();
			intent.putExtra("TYPE", 2);
			intent.putExtra("src", lat + "," + lng);

			super.onResume();

		}
		trackUser(add, Double.parseDouble(slat), Double.parseDouble(slong));
	}

	public void plotPoints(String s) {
		String tokens[] = s.split("YX");
		ArrayList<GPSModel> a = new ArrayList<GPSModel>();
		for (int i = 0; i < tokens.length; i++) {
			String t = tokens[i];
			String latlng[] = t.split("_");
			String lat = latlng[0];
			lat = lat.replace("X", "");
			lat = lat.replace("Y", "");
			String lng = latlng[1];
			lng = lng.replace("X", "");
			lng = lng.replace("Y", "");
			GPSModel g = new GPSModel();
			g.setLat(Double.parseDouble(lat));
			g.setLng(Double.parseDouble(lng));
			try {
				String[] address = MapGeocoder.getLocation(lat + "," + lng);
				g.setAddress(address[0]);

			} catch (Exception e) {
			}
			g.setTitle(title);
			g.setIcon(bd.getBitmap());
			a.add(g);
		}
		getIntent().putExtra("TYPE", 4);
		getIntent().putExtra("multiMarker", a);
		super.onResume();
	}

}
