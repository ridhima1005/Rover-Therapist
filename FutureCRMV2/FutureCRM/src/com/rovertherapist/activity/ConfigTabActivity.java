package com.rovertherapist.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.helper.AndroidConstants;
import com.helper.StringHelper;

public class ConfigTabActivity extends CommonActivity {

	public static String TAG = "WelcomeActivity";
	EditText ipaddress = null, port = null, taxi1km = null, taxirates = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		TabHost tabs = (TabHost) findViewById(R.id.tabHost);
		android.os.StrictMode.ThreadPolicy tp = android.os.StrictMode.ThreadPolicy.LAX;
		android.os.StrictMode.setThreadPolicy(tp);
		tabs.setup();

		TabHost.TabSpec spec1 = tabs.newTabSpec("tag1");

		spec1.setContent(R.id.tab1);

		spec1.setIndicator("", getResources().getDrawable(R.drawable.settings));

		tabs.addTab(spec1);

		// Server Settings
		ipaddress = (EditText) findViewById(R.id.editText1);
		port = (EditText) findViewById(R.id.editText2);
		taxi1km = (EditText) findViewById(R.id.editText3);
		taxirates = (EditText) findViewById(R.id.editText4);
		SharedPreferences s = PreferenceManager
				.getDefaultSharedPreferences(ConfigTabActivity.this);
		ipaddress.setText(s.getString("MAIN_SERVER_IP",
				AndroidConstants.MAIN_SERVER_IP + ""));
		port.setText(s.getString("MAIN_SERVER_PORT",
				AndroidConstants.MAIN_SERVER_PORT + ""));
		taxi1km.setText(s.getString("taxiRates1stkm", "17"));
		taxirates.setText(s.getString("taxirates", "10"));
	}

	public void toast(String message) {
		Toast t = Toast.makeText(ConfigTabActivity.this, message,
				Toast.LENGTH_LONG);
		t.show();
	}

	ProgressDialog progressDialog = null;

	public void fnConfig(View v) {

		String newIp = ipaddress.getText().toString().trim();
		int newPort = StringHelper.nullObjectToIntegerEmpty(port.getText()
				.toString());
		SharedPreferences s = PreferenceManager
				.getDefaultSharedPreferences(ConfigTabActivity.this);
		SharedPreferences.Editor editor = s.edit();
		editor.putString("MAIN_SERVER_IP", newIp);
		editor.putString("MAIN_SERVER_PORT", String.valueOf(newPort));
		editor.putString("taxiRates1stkm",
				String.valueOf(taxi1km.getText().toString()));
		editor.putString("taxirates",
				String.valueOf(taxirates.getText().toString()));
		editor.commit();

		boolean success = LoginActivity.checkConnectivityServer(s.getString(
				"MAIN_SERVER_IP", AndroidConstants.MAIN_SERVER_IP), Integer
				.parseInt(s.getString("MAIN_SERVER_PORT",
						AndroidConstants.MAIN_SERVER_PORT)));
		if (success) {
			go(LoginActivity.class);

		} else {
			toast("Not able to connect");
		}

	}

	public void guest(View v) {
		go(HomeActivity.class);
	}

}
