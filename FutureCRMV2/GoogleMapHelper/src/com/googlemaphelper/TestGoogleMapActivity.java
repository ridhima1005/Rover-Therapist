package com.googlemaphelper;

import java.io.InputStream;
import java.util.ArrayList;

import map.helper.GPSModel;
import map.helper.PlacesAutoCompleteAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;

public class TestGoogleMapActivity extends Activity {

	public AutoCompleteTextView from, to;
	public Bitmap bitmap;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
		android.os.StrictMode.setThreadPolicy(tp);
		from = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		to = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
		from.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		to.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));

	}

	public void onButtonClick(View v) {
		System.out.println("From " + from.getText());
		System.out.println("To " + to.getText());
		Intent intent = new Intent(getApplicationContext(),
				GoogleMapActivity.class);
		String f = from.getText().toString();
		String t = to.getText().toString();

		GPSModel gm = new GPSModel();
		ArrayList<GPSModel> list = new ArrayList<GPSModel>();
		for (int i = 0; i < list.size(); i++) {

			gm.setAddress(list.get(i).toString());
			gm.setTitle(list.get(i).toString());
		}

		InputStream is = getResources().openRawResource(R.drawable.bitmap);
		Bitmap bitmap = BitmapFactory.decodeStream(is);

		intent.putExtra("TYPE", 1);
		intent.putExtra("SHOW_DISTANCE_TIME", true);
		intent.putExtra("showDirections", true);

		intent.putExtra("src", f);
		intent.putExtra("src_title", "Source ");
		intent.putExtra("src_desc", f);
		intent.putExtra("BitmapImage", bitmap);

		intent.putExtra("dest", t);
		intent.putExtra("dest_title", "Destination");
		intent.putExtra("dest_desc", t);

		startActivity(intent);
	}

}
