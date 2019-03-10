package com.rovertherapist.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.helper.AndroidConstants;

public class LaunchComplaint extends Activity {

	EditText txtVehiclenumber = null;
	EditText txtcomplaint = null;
	String src, dest = null;
	Float orignalFare = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launchcomplaint);
		txtVehiclenumber = (EditText) findViewById(R.id.txtVechicleNumber);
		txtcomplaint = (EditText) findViewById(R.id.txtComplaint);
		src = getIntent().getStringExtra("src");
		dest = getIntent().getStringExtra("dest");
		orignalFare = getIntent().getFloatExtra("orignalfare", 0f);
		if (src != null && dest != null && orignalFare != null) {
			txtcomplaint.append("Start : " + src + "\nDestination : " + dest
					+ "\nOrginal Fare : " + orignalFare + "\nFare Taken : ");
		}
	}

	public void sendcomplaint(View view) {

		try {
			String phoneNo = AndroidConstants.COMPLAINT_NUMBER;
			System.out.println("reading complaint ....");
			String complaint = txtcomplaint.getText().toString();
			System.out.println("read complaint ...." + complaint);
			String vehicleno = txtVehiclenumber.getText().toString();
			System.out.println("read veh No ...." + vehicleno);

			String message = "Complaint for Vehicle No. " + vehicleno + " :-  "
					+ complaint;
			System.out.println("Message to send : " + message);
			if (vehicleno.length() > 0 && message.length() > 0
					&& message != null && vehicleno != null)
				sendSMS(phoneNo, message);
			else
				Toast.makeText(getBaseContext(),
						"Please enter both Vehicle number and complaint.",
						Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			System.out.println("in exception "
					+ txtcomplaint.getText().toString());
			Toast.makeText(getApplicationContext(),
					"in exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	private void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
				SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS sent",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Generic failure",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No service",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "Null PDU",
							Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "Radio off",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS delivered",
							Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS not delivered",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		ArrayList<String> parts = sms.divideMessage(message);
		sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
		Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG)
				.show();
		// sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}
}
