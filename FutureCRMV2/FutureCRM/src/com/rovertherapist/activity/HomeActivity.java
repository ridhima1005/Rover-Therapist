package com.rovertherapist.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import map.helper.MapGeocoder;
import map.helper.PlacesAutoCompleteAdapter;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.entities.DayEntity;
import com.entities.PlaceDetailEntity;
import com.entities.PlaceEntity;
import com.entities.ReviewEntity;
import com.helper.AndroidConstants;
import com.helper.CategoryConstants;
import com.helper.IPlacesHelper;
import com.helper.PlacesHelper;
import com.helper.StringHelper;
import com.helper.TempratureHelper;

public class HomeActivity extends CommonActivity implements
		ActionBar.OnNavigationListener, NumberPicker.OnValueChangeListener {
	// action bar
	private ActionBar actionBar;
	public HashMap placeImage = null;
	
	// Title navigation Spinner data
	private ArrayList<SpinnerNavItem> navSpinner;

	// Navigation adapter
	private TitleNavigationAdapter adapter;

	private ListView listViewDisasterList;

	ProgressBar progress;

	private GPSTracker g;

	private SharedPreferences s;

	String type = CategoryConstants.ATTRACTIONS;
	String distance = "1000";
	String addressStr = "";
	private IPlacesHelper placesHelper;
	private TextView textViewWeather;
	private TextView textViewCelcius;
	private TextView buttonDistance;
	private AutoCompleteTextView txtQuery = null;
	private TextView buttonType;
	private ImageView weatherImageView = null;
	private int createFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		txtQuery = (AutoCompleteTextView) findViewById(R.id.disasterDetailsDesc);

		txtQuery.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item));

		actionBar = getActionBar();
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(false);

		// Enabling Spinner dropdown navigation
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerNavItem>();
		navSpinner.add(new SpinnerNavItem("Places", R.drawable.home));
		navSpinner.add(new SpinnerNavItem("You on Map", R.drawable.maps));
		navSpinner.add(new SpinnerNavItem("Change Category",
				R.drawable.ic_launcher));
		navSpinner.add(new SpinnerNavItem("Change Distance",
				R.drawable.ic_launcher));

		navSpinner.add(new SpinnerNavItem("Reset", R.drawable.home));
		navSpinner.add(new SpinnerNavItem("Settings", R.drawable.settings));
		navSpinner.add(new SpinnerNavItem("Launch Complaint", R.drawable.end));
		navSpinner.add(new SpinnerNavItem("Exit", R.drawable.dialog_cancel));

		// title drop down adapter
		adapter = new TitleNavigationAdapter(getApplicationContext(),
				navSpinner);

		// assigning the spinner navigation
		actionBar.setListNavigationCallbacks(adapter, this);

		listViewDisasterList = (ListView) findViewById(R.id.listView1);
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		g = new GPSTracker(getApplicationContext());
		progress.setVisibility(ProgressBar.VISIBLE);
		new Thread(new Runnable() {

			//get current location
			@Override
			public void run() {
				final Location l = g.getLocation();
				final String add1 = MapGeocoder.getLocation(l.getLatitude()
						+ "," + l.getLongitude())[0];
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						txtQuery.setText(add1);

						progress.setVisibility(ProgressBar.INVISIBLE);

					}
				});
				Double[] latLong = MapGeocoder.getLatlngOfAddress(txtQuery
						.getText().toString());

				refresh1(latLong);

			}
		}).start();

		s = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
		type = s.getString("types", CategoryConstants.ATTRACTIONS);
		distance = s.getString("distance", "1000");
		placesHelper = new PlacesHelper();
		buttonDistance = (TextView) findViewById(R.id.textView4);
		weatherImageView = (ImageView) findViewById(R.id.imageViewWeather);
		textViewWeather = (TextView) findViewById(R.id.textViewWeather);

		buttonType = (TextView) findViewById(R.id.category);
		System.out.println("Home oncreate");
		addressStr = this.getIntent().getStringExtra("addr");

		System.out.println("addressStr.................................."
				+ addressStr);

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

	@Override
	protected void onResume() {
		super.onResume();
		createFlag++;
		System.out.println("TouristHome onResume");
		progress.setVisibility(ProgressBar.INVISIBLE);
		System.out.println("Distance = " + distance);
		System.out.println("Distance Shared = "
				+ s.getString("distance", "1000"));
		System.out.println("type = " + type);
		String typecheck = s.getString("types", CategoryConstants.ATTRACTIONS);
		String typeExtra = s.getString("types", CategoryConstants.ATTRACTIONS);
		if (typecheck.contains("Attractions")) {
			typecheck = typecheck.replace("Attractions",
					CategoryConstants.ATTRACTIONS);
		}
		buttonDistance
				.setText(String.valueOf(Integer.parseInt(distance) / 1000));

		String Buttext = typeExtra;
		System.out.println("Types 1 : " + type);
		if (typeExtra != null) {
			System.out.println("Types Extra1 : " + typeExtra);
			if (typeExtra.contains("Attractions")) {
				typeExtra = typeExtra.replace("Attractions",
						CategoryConstants.ATTRACTIONS);
				System.out.println("Types Extra 2 : " + typeExtra);
			}
			type = typeExtra;
			System.out.println("Types  2 : " + type); //when many attractions are selected
			String[] typeArr = Buttext.split("\\|");
			if (typeArr.length > 1) {
				buttonType.setText(typeArr.length + " Categories");
			} else {
				buttonType.setText(typeArr[0]);
			}
		}

		System.out
				.println("TouristHome Distance............................................................................... = "
						+ distance);
		System.out.println("TouristHome Distance Shared = "
				+ s.getString("distance", "1000"));
		System.out.println("TouristHome type = " + type);
		System.out.println("TouristHome type Shared = " + typecheck);
		if ((!distance.equals(s.getString("distance", "1000")))
				|| !(type.equals(typecheck))
				|| listViewDisasterList.getCount() == 0) {
			if (addressStr != null && addressStr.length() > 0) {
				System.out
						.println("1............................................................................... = "
								+ distance);
				txtQuery.setText(addressStr);

				refresh();
			} else {
				System.out
						.println("2............................................................................... = "
								+ distance);
			}
		}

	}

	//public void setAddress() {
	//}

	public void popupImage() {
		toast("Places " + placeImage.size());
		PopupImage pi = new PopupImage(HomeActivity.this, placeImage);
		pi.showImage();
	}

	public String[] getList() {

		String imageInSD = AndroidConstants.BASE_FILE_PATH;
		File f = new File(imageInSD);
		String[] lat = f.list();
		return lat;

	}

	//algorithm
	public static double distance(double lat1, double lon1, double lat2,
			double lon2) { // in KM
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist);
	}

	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public HashMap getMatchedImages(double mlat, double mlng, int distanceSele) {

		distanceSele = distanceSele / 1000;
		HashMap map = new HashMap();
		String list[] = getList();
		if (list != null) {
			int cnt = 0;
			for (int i = 0; i < list.length; i++) {
				try {
					System.out.println("File Name " + list[i]);
					String latlng = list[i];
					latlng = latlng.substring(0, latlng.lastIndexOf("."));
					double lat = Double.parseDouble(latlng.split(",")[0]);
					double lng = Double.parseDouble(latlng.split(",")[1]);
					if (lat == 0 || lng == 0) {
						continue;
					}
					double distance = distance(mlat, mlng, lat, lng);
					System.out.println("My Location lat lng " + distance
							+ " Expencted " + distanceSele);
					if (distance < distanceSele) {

						System.out.println("My Location lat lng " + list[i]);
						map.put(cnt, list[i]);
						cnt++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;

	}

	private void refresh() {

		System.out.println("TouristHome refresh"
				+ txtQuery.getText().toString());
		distance = s.getString("distance", "1000");
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				progress.setVisibility(ProgressBar.VISIBLE);

			}
		});

		//information of place
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (g.canGetLocation) {
					final Double[] latlong = MapGeocoder
							.getLatlngOfAddress(txtQuery.getText().toString());
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							progress.setVisibility(ProgressBar.INVISIBLE);

						}
					});
					final List<PlaceEntity> places = placesHelper.searchPlaces(
							String.valueOf(latlong[1]),
							String.valueOf(latlong[0]), distance, type);
					int cnt = 0;
					placeImage = new HashMap();

					for (PlaceEntity place : places) {
						System.out.println("------------------Place :"
								+ place.getLatitude() + ","
								+ place.getLongitude());
						System.out.println("------------------Place ID :"
								+ place.getId());
						System.out.println("------------------Place Name :"
								+ place.getDetailEntity().getName());
						System.out.println("------------------Place Url :"
								+ place.getDetailEntity().getUrl());
						System.out.println("------------------Place Address :"
								+ place.getDetailEntity()
										.getFormatted_address());

						ReviewEntity[] reviewEntities = place.getDetailEntity()
								.getReviews();
						System.out
								.println("------------------Place  Review-----------------------");
						if (reviewEntities != null) {
							for (int i = 0; i < reviewEntities.length; i++) {
								System.out.println("Author :"
										+ reviewEntities[i].getAuthorName());
								System.out.println("Text :"
										+ reviewEntities[i].getText());
							}
						} else {
							System.out
									.println("------------------NO Place  Review-----------------------");
						}

						DayEntity[] dayEntities = place.getDetailEntity()
								.getOpening_HRS();
						System.out
								.println("------------------Place  Working Hours-----------------------");
						if (dayEntities != null) {
							System.out.println("------------------Place Open :"
									+ place.getDetailEntity().isOpen_now());
							for (int i = 0; i < dayEntities.length; i++) {
								try {
									System.out.println(" -----Day------"
											+ dayEntities[i].getDay());
									System.out.println(" -----Open Time------"
											+ dayEntities[i].getOpenHRS());
									System.out.println(" -----Close Time------"
											+ dayEntities[i].getCloseHRS());
								} catch (Exception e) {
									System.out
											.println("------------------NO Working HRS  Data for-----------------------");
								}
							}
						} else {
							System.out
									.println("------------------NO Working HRS  Data-----------------------");
						}

					}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							if (places.size() == 0) {
								PlaceEntity entity = new PlaceEntity();
								PlaceDetailEntity detailEntity = new PlaceDetailEntity();
								detailEntity.setName("No Data Found");
								entity.setDetailEntity(detailEntity);
								places.add(entity);
							}

							CustomListViewAdapter adapter = new CustomListViewAdapter(
									HomeActivity.this,
									android.R.layout.simple_list_item_1,
									places, latlong);
							popupImage();
							System.out
									.println("------------------adapter created-----------------------");
							listViewDisasterList.setAdapter(adapter);

							// Popup image places start
							try {
								if (places != null && places.size() > 0) {
									PlaceEntity place = places.get(0);
									placeImage = getMatchedImages(
											place.getLatitude(),
											place.getLongitude(),
											StringHelper.n2i(distance));
									if (placeImage != null
											&& placeImage.size() > 0) {
										popupImage();
									}

								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							// Popup image places end
							progress.setVisibility(ProgressBar.INVISIBLE);
						}
					});

				}
			}
		}).start();

	}

	public void refresh1(final Double[] latLong) {

		System.out.println("TouristHome refresh");
		distance = s.getString("distance", "1000");
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				progress.setVisibility(ProgressBar.VISIBLE);

			}
		});

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (latLong != null) {
					final Location l = g.getLocation();
					SharedPreferences s = PreferenceManager
							.getDefaultSharedPreferences(HomeActivity.this);

					SharedPreferences.Editor editor = s.edit();
					editor.putString("latitude", String.valueOf(latLong[0]));
					editor.putString("longitude", String.valueOf(latLong[1]));
					editor.commit();
					runOnUiThread(new Runnable() {

						//weather
						@Override
						public void run() {
							String recommended = updateTemprature(latLong);
							String tokens[] = recommended.split("#");
							for (int i = 0; i < tokens.length; i++) {
								if (tokens[i].length() > 0) {
									if (type.indexOf(tokens[i]) == -1) {
										type = type + "|" + tokens[i];
									}
								}
							}
							if (type.startsWith("|")) {
								type = type.substring(1);
							}
							if (type.endsWith("|")) {
								type = type.substring(0, type.length() - 1);
							}
							toast("Final Types " + type);
						}
					});

					final List<PlaceEntity> places = placesHelper.searchPlaces(
							String.valueOf(latLong[1]),
							String.valueOf(latLong[0]), distance, type);

					if (places != null && places.size() > 0)
						for (PlaceEntity place : places) {

							System.out.println("------------------Place :"
									+ place.getLatitude() + ","
									+ place.getLongitude());
							System.out.println("------------------Place ID :"
									+ place.getId());
							System.out.println("------------------Place Name :"
									+ place.getDetailEntity().getName());

							System.out.println("------------------Place Url :"
									+ place.getDetailEntity().getUrl());
							System.out
									.println("------------------Place Address :"
											+ place.getDetailEntity()
													.getFormatted_address());

							ReviewEntity[] reviewEntities = place
									.getDetailEntity().getReviews();
							System.out
									.println("------------------Place  Review-----------------------");
							if (reviewEntities != null) {
								for (int i = 0; i < reviewEntities.length; i++) {
									System.out.println("Author :"
											+ reviewEntities[i].getAuthorName());
									System.out.println("Text :"
											+ reviewEntities[i].getText());
								}
							} else {
								System.out
										.println("------------------NO Place  Review-----------------------");
							}

							DayEntity[] dayEntities = place.getDetailEntity()
									.getOpening_HRS();
							System.out
									.println("------------------Place  Working Hours-----------------------");
							if (dayEntities != null) {
								System.out
										.println("------------------Place Open :"
												+ place.getDetailEntity()
														.isOpen_now());
								for (int i = 0; i < dayEntities.length; i++) {
									try {
										System.out.println(" -----Day------"
												+ dayEntities[i].getDay());
										System.out
												.println(" -----Open Time------"
														+ dayEntities[i]
																.getOpenHRS());
										System.out
												.println(" -----Close Time------"
														+ dayEntities[i]
																.getCloseHRS());
									} catch (Exception e) {
										System.out
												.println("------------------NO Working HRS  Data for-----------------------");
									}
								}
							} else {
								System.out
										.println("------------------NO Working HRS  Data-----------------------");
							}

						}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							if (places != null && places.size() == 0) {
								PlaceEntity entity = new PlaceEntity();
								PlaceDetailEntity detailEntity = new PlaceDetailEntity();
								detailEntity.setName("No Data Found");
								entity.setDetailEntity(detailEntity);
								places.add(entity);
							}

							// Popup image places start
							try {
								if (places != null && places.size() > 0) {
									CustomListViewAdapter adapter = new CustomListViewAdapter(
											HomeActivity.this,
											android.R.layout.simple_list_item_1,
											places, latLong);
									System.out
											.println("------------------adapter created-----------------------");
									listViewDisasterList.setAdapter(adapter);
									PlaceEntity place = places.get(0);
									placeImage = getMatchedImages(
											place.getLatitude(),
											place.getLongitude(),
											StringHelper.n2i(distance));
									if (placeImage != null
											&& placeImage.size() > 0) {
										popupImage();
									}

								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							// Popup image places end
							try {
								progress.setVisibility(ProgressBar.INVISIBLE);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
				}

			}
		}).start();

	}

	String ctemp = "";
	public static String recommended = "";

	//weather
	public String updateTemprature(Double[] latLong) {
		try {
			HashMap map = TempratureHelper.getWeatherData(latLong[0] + "",
					latLong[1] + "");

			String id = StringHelper.n2s(map.get("id"));
			String details = StringHelper.n2s(TempratureHelper.map.get(id));
			String token[] = details.split("#");
			String picture = token[1];
			String weather = token[2];

			double temp = StringHelper.n2d(map.get("temp"));

			if (temp > 0 && temp <= 15) {
				ctemp = "cold";
			} else if (temp > 15 && temp <= 20) {
				ctemp = "mid";
			} else if (temp > 20 && temp <= 25) {
				ctemp = "mid";
			} else {
				ctemp = "hot";
			}
			int h = new Date().getHours();
			textViewWeather.setText("" + Math.round(temp) + " Deg " + token[0]);

			if (!picture.equalsIgnoreCase("NA")) {
				picture = picture.substring(0, picture.length() - 4);
				String mDrawableName = "s" + picture;
				int resID = getResources().getIdentifier(mDrawableName,
						"drawable", getPackageName());
				weatherImageView.setImageResource(resID);
			}
			String c_daytime = "";
			if (h >= 7 && h <= 12) {
				c_daytime = "mor";
			} else if (h > 12 && h <= 16) {
				c_daytime = "after";
			} else if (h > 16) {
				c_daytime = "eve";
			}

			for (int i = 0; i < CategoryConstants.weatherWiseClassfication.length; i++) {
				String[] tokens = CategoryConstants.weatherWiseClassfication[i];
				String daytime = tokens[1];
				String weathr = tokens[2];
				String category = tokens[0];
				if (daytime.indexOf(c_daytime) != -1
						&& weathr.indexOf(ctemp) != -1) {
					HomeActivity.recommended += "#" + category;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return recommended;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_refresh:
			Double[] latLong = MapGeocoder.getLatlngOfAddress(txtQuery
					.getText().toString());
			if (!(latLong[0] == null || latLong[1] == null
					|| latLong[0].toString().equalsIgnoreCase("null") || latLong[1]
					.toString().equalsIgnoreCase("null"))) {

				refresh1(latLong);
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void getDirection() {
		Intent i = new Intent(HomeActivity.this, GetDirections.class);
		startActivity(i);
	}

	@Override
	public boolean onNavigationItemSelected(int pos, long itemId) {
		switch (pos) {
		case 4:
			// category
			progress.setVisibility(ProgressBar.VISIBLE);
			new Thread(new Runnable() {

				@Override
				public void run() {
					final Location l = g.getLocation();
					final String add1 = MapGeocoder.getLocation(l.getLatitude()
							+ "," + l.getLongitude())[0];
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							txtQuery.setText(add1);
							Double[] latLong = { l.getLatitude(),
									l.getLongitude() };
							refresh1(latLong);

						}
					});

				}
			}).start();
			return true;
		case 1:
			// on map
			Intent intent = new Intent(this, MapActivity.class);
			intent.putExtra("lat", String.valueOf(g.getLatitude()));
			intent.putExtra("long", String.valueOf(g.getLongitude()));
			intent.putExtra("add", String.valueOf(txtQuery.getText()));

			startActivity(intent);
			return true;
		case 2:
			// category
			clickTypes(new View(this));
			return true;
		case 3:
			// distance
			clickDistance(new View(this));
			return true;
		case 5:
			// distance
			go(ConfigTabActivity.class);
			return true;
		case 6:
			// launch complaint
			go(LaunchComplaint.class);
			return true;

		case 7:
			// exit
			finish();
			moveTaskToBack(true);
			return true;

		default:
			return false;
		}

	}

	public void clickTypes(View view) {

		Intent intent = new Intent(HomeActivity.this, TypesList.class);
		intent.putExtra("addr", txtQuery.getText().toString());
		HomeActivity.this.startActivity(intent);

	}

	//select distance dialog box
	public void clickDistance(View view) {

		final Dialog d = new Dialog(HomeActivity.this);
		d.setTitle("Select Distance in KM");
		d.setContentView(R.layout.selectdistance);
		Button b1 = (Button) d.findViewById(R.id.button1);
		Button b2 = (Button) d.findViewById(R.id.button2);
		final NumberPicker np = (NumberPicker) d
				.findViewById(R.id.numberPicker1);
		np.setMaxValue(50); // max value 100
		np.setMinValue(1); // min value 0
		np.setValue(Integer.parseInt(distance) / 1000);
		np.setWrapSelectorWheel(false);
		np.setOnValueChangedListener(this);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String selectedDist = String.valueOf(np.getValue());
				buttonDistance.setText(selectedDist);
				distance = String.valueOf(Integer.parseInt(selectedDist) * 1000);
				Toast.makeText(getApplicationContext(),
						"Distance : " + selectedDist, Toast.LENGTH_SHORT)
						.show();
				SharedPreferences s = PreferenceManager
						.getDefaultSharedPreferences(HomeActivity.this);
				SharedPreferences.Editor editor = s.edit();
				editor.putString("distance", distance);
				editor.commit();
				d.dismiss();
				refresh();
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				d.dismiss(); // dismiss the dialog
			}
		});
		d.show();
	}

	@Override
	public void onValueChange(NumberPicker arg0, int arg1, int arg2) {

	}

}
