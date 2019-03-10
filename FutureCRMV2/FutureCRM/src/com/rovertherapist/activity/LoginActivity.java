package com.rovertherapist.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;

import com.helper.AndroidConstants;
import com.helper.HttpView;
import com.helper.StringHelper;

public class LoginActivity extends CommonActivity {

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
		android.os.StrictMode.setThreadPolicy(tp);

		mHandler = new Handler();
	}

	protected int _splashTime = 2000;

	@Override
	// whenever the app starts again start showing the splash screens from start
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(_splashTime);
					Thread.currentThread().interrupt();
				} catch (InterruptedException e) {
					e.printStackTrace();
					// do nothing
				} finally {
					checkConnectivity();
				}
			}
		}.start();

	}

	GPSTracker g = null;

	public void checkConnectivity() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences s = PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this);

				boolean success = checkConnectivityServer();
				if (success) {
					SharedPreferences.Editor editor = s.edit();
					editor.putString("MAIN_SERVER_IP",
							AndroidConstants.MAIN_SERVER_IP + "");
					editor.putString("MAIN_SERVER_PORT",
							AndroidConstants.MAIN_SERVER_PORT + "");
					editor.commit();
					checkUserDetails();

				} else {

					String MAIN_SERVER_IP = s.getString("MAIN_SERVER_IP",
							AndroidConstants.MAIN_SERVER_IP);
					String MAIN_SERVER_PORT = s.getString("MAIN_SERVER_PORT",
							AndroidConstants.MAIN_SERVER_PORT);
					if (!MAIN_SERVER_IP
							.equalsIgnoreCase(AndroidConstants.MAIN_SERVER_IP)
							|| !MAIN_SERVER_PORT
									.equalsIgnoreCase(AndroidConstants.MAIN_SERVER_PORT)) {
						success = checkConnectivityServer(MAIN_SERVER_IP,
								StringHelper.n2i(MAIN_SERVER_PORT));
						if (success) {
							AndroidConstants.MAIN_SERVER_IP = MAIN_SERVER_IP;
							AndroidConstants.MAIN_SERVER_PORT = MAIN_SERVER_PORT;
							checkUserDetails();
						} else {
							System.out.println("Redirecting to Config 1");

							mHandler.post(new Runnable() {

								@Override
								public void run() {
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
											LoginActivity.this);

									// set title
									alertDialogBuilder
											.setTitle("Server not available ");

									// set dialog message
									alertDialogBuilder
											.setMessage("Sign in as guest ?")
											.setCancelable(false)
											.setPositiveButton(
													"Yes",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int id) {
															go(RegisterActivity.class);

														}
													})
											.setNegativeButton(
													"No",
													new DialogInterface.OnClickListener() {
														public void onClick(
																DialogInterface dialog,
																int id) {
															go(ConfigTabActivity.class);

														}
													});

									// create alert dialog
									AlertDialog alertDialog = alertDialogBuilder
											.create();

									alertDialog.show();
								}

							});
						}
					} else {
						System.out.println("Redirecting to Config 2");
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										LoginActivity.this);

								// set title
								alertDialogBuilder
										.setTitle("Server not available ");

								// set dialog message
								alertDialogBuilder
										.setMessage("Sign in as guest ?")
										.setCancelable(false)
										.setPositiveButton(
												"Yes",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														go(HomeActivity.class);

													}
												})
										.setNegativeButton(
												"No",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														go(ConfigTabActivity.class);

													}
												});

								// create alert dialog
								AlertDialog alertDialog = alertDialogBuilder
										.create();
								alertDialog.show();

							}
						});
					}
				}
			}
		});

	}

	public void checkUserDetails() {
		g = new GPSTracker(LoginActivity.this);
		if (g.canGetLocation) {
			Location l = g.getLocation();
			toast(l.getLatitude() + " " + l.getLongitude());
		}
		String url = AndroidConstants.MAIN_URL() + "&method=userDetails&imei="
				+ getIMEI();
		int ret = StringHelper.n2i(HttpView.connectToServer(url).trim());
		if (ret == 0) {
			go(RegisterActivity.class);
		} else {
			go(HomeActivity.class);
		}

		finish();
	}

}
