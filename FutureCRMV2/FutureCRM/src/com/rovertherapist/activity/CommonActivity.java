package com.rovertherapist.activity;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.Toast;

import com.google.gson.Gson;
import com.helper.AndroidConstants;
import com.helper.HttpView;
import com.helper.StringHelper;
import com.helper.UserModel;

@SuppressLint("NewApi")
public class CommonActivity extends Activity {
	static int cnt;

	public static void CancelNotification(Context ctx, int notifyId) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nMgr = (NotificationManager) ctx
				.getSystemService(ns);
		nMgr.cancel(notifyId);
	}

	public void setUserModel(String json) {
		Gson g = new Gson();
		UserModel um = g.fromJson(json, UserModel.class);
		AndroidConstants.currentUser = um;
	}

	public void goClass(String c1) {
		Class c = null;
		try {
			System.out.println("i am in here " + c1);
			c = Class.forName(c1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = new Intent(CommonActivity.this, c);
		startActivity(intent);
	}

	public void go(Class c) {
		Intent intent = new Intent(CommonActivity.this, c);
		startActivity(intent);
		finish();
	}

	public void showOnMap(String dlatlong, String elatLong) {
		Intent intent = new Intent(CommonActivity.this, HomeActivity.class);
		intent.putExtra("dlatlong", dlatlong);
		intent.putExtra("elatlong", elatLong);
		startActivity(intent);
	}

	//successful connection
	public boolean checkConnectivityServer() {
		boolean success = checkConnectivityServer(
				AndroidConstants.MAIN_SERVER_IP,
				StringHelper
						.nullObjectToIntegerEmpty(AndroidConstants.MAIN_SERVER_PORT));
		return success;

	}
	
//unsuccessful connection
	public static boolean checkConnectivityServer(String ip, int port) {
		boolean success = false;
		try {
			System.out.println("Checking Connectivity With " + ip + " " + port);
			Socket soc = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(ip, port);
			soc.connect(socketAddress, 3000);
			success = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(" Connecting to server " + success);
		return success;

	}

	ProgressDialog progressDialog = null;
	AlertDialog alertDialog = null;

	class CheckConnectivityAsyncTask extends AsyncTask<String, String, String> {
		String message = "";
		String title = "";
		String action = "";

		@Override
		protected void onPreExecute() {
			System.out.println("In Aysnc");
			progressDialog = ProgressDialog.show(CommonActivity.this,
					"Please Wait", "Loading....", true);
			alertDialog = new AlertDialog.Builder(CommonActivity.this).create();
		}

		@Override
		protected String doInBackground(String... params) {
			String ip = params[0];
			int port = StringHelper.nullObjectToIntegerEmpty(params[1]);
			boolean success = HttpView.checkConnectivityServer(ip, port);

			if (success) {

				title = "Success";
				if (params.length > 2 && params[2].equalsIgnoreCase("UpdateIp")) {
					action = "1";
					message = "Connection established with the Main Server.";
					AndroidConstants.MAIN_SERVER_IP = ip;
					AndroidConstants.MAIN_SERVER_PORT = port + "";
				} else {
					message = "Internet Connection Successful!";
				}
			} else {
				action = "";
				message = "Error Connecting to Server http://" + ip + ":"
						+ port;
				title = "Connectivity Error";
			}

			return success + "";
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			alertDialog.setTitle(title);
			alertDialog.setMessage(message);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					alertDialog.hide();
					if (action.length() > 0) {

						Intent main = new Intent(CommonActivity.this,
								LoginActivity.class);
						startActivity(main);
						finish();

					}

				}
			});
			alertDialog.show();

		};

	}

	//toasts
	public void toast(String message) {
		System.out.println(message);
		Toast t = Toast.makeText(CommonActivity.this, message, 1000);
		t.show();
	}

	public void notifyme(String contentTitle, String contentText) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = contentTitle;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		Context context = getApplicationContext();
		Intent notificationIntent = new Intent(CommonActivity.this,
				LoginActivity.class);

		PendingIntent contentIntent = PendingIntent.getActivity(
				CommonActivity.this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		final int HELLO_ID = 1;
		mNotificationManager.notify(HELLO_ID, notification);
	};

	public void finished() {
		try {
			System.runFinalizersOnExit(true);
			finish();
			super.finish();
			super.onDestroy();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			android.os.Process.killProcess(android.os.Process.myPid());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		System.out.println("Device IMEI is " + imei);
		return imei;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	Object returnObject = null;

	private class AsyncTaskRunner extends AsyncTask<String, String, Object> {
		@Override
		protected Object doInBackground(String... params) {
			publishProgress("Connecting to Server..."); // Calls
														// onProgressUpdate()
			try {
				returnObject = null;
				// Do your long operations here and return the result
				String url = params[0];
				returnObject = HttpView.connect2ServerObject(url);

			} catch (Exception e) {
				e.printStackTrace();

			}
			return returnObject;
		}

		@Override
		protected void onPostExecute(Object result) {
			// execution of result of Long time consuming operation
			progressDialog.setTitle("Success");
			progressDialog.dismiss();
			returnObject = result;
		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog

			progressDialog = ProgressDialog.show(CommonActivity.this,
					"Please Wait", "Loading....", true);
		}

		@Override
		protected void onProgressUpdate(String... text) {
			progressDialog.setTitle(text[0]);
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}
	};

}
