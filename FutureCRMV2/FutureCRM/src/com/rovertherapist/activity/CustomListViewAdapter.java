package com.rovertherapist.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import map.helper.GPSModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.entities.PlaceEntity;
import com.googlemaphelper.GoogleMapActivity;

public class CustomListViewAdapter extends ArrayAdapter<PlaceEntity> {

	Context context;
	HashMap areaType = new HashMap();
	HashMap severityType = new HashMap();
	Double[] latlong = null;

	public CustomListViewAdapter(Context context, int resourceId,
			List<PlaceEntity> items, Double[] latlong) {
		super(context, resourceId, items);
		this.context = context;
		this.latlong = latlong;
		areaType.clear();
		areaType.put("1", "Area");
		areaType.put("2", "Road");
		areaType.put("3", "Point or Building");
		severityType.clear();
		severityType.put("1", "High");
		severityType.put("2", "Medium");
		severityType.put("3", "Low");
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
		Button viewonmap;
		ImageView evacuationicon;
		ImageView serverImageView;
		TextView disasterType;
		TextView textViewKM;
		TextView disasterArea;
		TextView disasterServerity;
		ImageView imageViewInfo;
		ImageView imageViewStar;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final PlaceEntity rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView
					.findViewById(R.id.disasterDetails);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.disasterDetailsDesc);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			holder.serverImageView = (ImageView) convertView
					.findViewById(R.id.serverImageView);
			holder.textViewKM = (TextView) convertView
					.findViewById(R.id.textViewKM1);
			holder.imageViewStar = (ImageView) convertView
					.findViewById(R.id.imageViewStar);

			

			holder.evacuationicon = (ImageView) convertView
					.findViewById(R.id.evacuationicon);
			holder.imageViewInfo = (ImageView) convertView
					.findViewById(R.id.imageViewInfo);
			holder.viewonmap = (Button) convertView
					.findViewById(R.id.viewonmap);

			holder.viewonmap.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					HomeActivity dh = (HomeActivity) context;
					dh.progress.setVisibility(ProgressBar.VISIBLE);
					Intent intent = new Intent(context, MapActivity.class);
					intent.putExtra("lat",
							String.valueOf(rowItem.getLatitude()));
					intent.putExtra("long",
							String.valueOf(rowItem.getLongitude()));
					intent.putExtra("add", String.valueOf(rowItem
							.getDetailEntity().getFormatted_address()));

					context.startActivity(intent);

				}
			});
			holder.evacuationicon
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context,
									GoogleMapActivity.class);
							SharedPreferences s = PreferenceManager
									.getDefaultSharedPreferences(context);

							ArrayList<GPSModel> a = new ArrayList<GPSModel>();

							String lat = String.valueOf(rowItem.getLatitude());
							lat = lat.replace("X", "");
							lat = lat.replace("Y", "");
							String lng = String.valueOf(rowItem.getLongitude());
							lng = lng.replace("X", "");
							lng = lng.replace("Y", "");

							String lat1 = s.getString("CURRENT_LAT", "");
							String lng1 = s.getString("CURRENT_LNG", "");

							intent.putExtra("TYPE", 1);
							intent.putExtra("SHOW_DISTANCE_TIME", true);
							intent.putExtra("src", lat1 + "," + lng1);
							intent.putExtra("src_title", "Source ");
							intent.putExtra("src_desc", "Source Description");

							intent.putExtra("dest", lat + "," + lng);
							intent.putExtra("dest_title", "Place");
							intent.putExtra("dest_desc", "Destination Desc");
							intent.putExtra("showDirections", true);
							intent.putExtra("showDirectionsTitle",
									"Place Directions");

							context.startActivity(intent);

						}
					});
			holder.imageViewInfo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					final AlertDialog alertDialog = new AlertDialog.Builder(
							context).create();

					alertDialog.setTitle("Place Details");
					String text = "Place Name - "
							+ rowItem.getDetailEntity().getName();
					text += "\n Address - "
							+ rowItem.getDetailEntity().getFormatted_address();
					text += "\n URL - " + rowItem.getDetailEntity().getUrl();
					text += "\n Rating -"
							+ rowItem.getDetailEntity().getUser_ratings();

					alertDialog.setMessage(text);
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									alertDialog.hide();

								}
							});
					alertDialog.show();

					// TODO Auto-generated method stub
					HomeActivity dh = (HomeActivity) context;
					dh.progress.setVisibility(ProgressBar.VISIBLE);
					Intent intent = new Intent(context, Place.class);
					intent.putExtra("placeName", rowItem.getDetailEntity()
							.getName());
					intent.putExtra("placeAdd", rowItem.getDetailEntity()
							.getFormatted_address());
					intent.putExtra("placeURL", rowItem.getDetailEntity()
							.getUrl());

					intent.putExtra("place", rowItem);

					context.startActivity(intent);

				}
			});
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		// if(rowItem.getImageName())
		int picId = R.drawable.compass;
		try {
			picId = context.getResources().getIdentifier("festival.png",
					"drawable", context.getPackageName());
		} catch (Exception e) {
			// TODO: handle exception
		}

		//near by places
		holder.txtTitle.setText(rowItem.getDetailEntity().getName());
		try {

			if (rowItem.getDistance() == 0) {
				holder.textViewKM.setText("Near");
			} else {
				holder.textViewKM.setText(rowItem.getDistance() + " KM");
			}

			if (rowItem.isServer()) {
				holder.serverImageView.setVisibility(ImageView.VISIBLE);
			} else {
				holder.serverImageView.setVisibility(ImageView.INVISIBLE);
			}
			try {
				if (HomeActivity.recommended.length() > 0) {
					boolean success = false;
					String tokens[] = HomeActivity.recommended.split("#");
					for (int i = 0; i < tokens.length; i++) {
						String t = tokens[i];
						if (t.length() > 0)
							if (rowItem.getDetailEntity().getType().indexOf(t) != -1) {
								success = true;
								break;
							}
					}
					if (success) {
						holder.imageViewStar.setVisibility(ImageView.VISIBLE);
					} else {
						holder.imageViewStar.setVisibility(ImageView.INVISIBLE);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}
}