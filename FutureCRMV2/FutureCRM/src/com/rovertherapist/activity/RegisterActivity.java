package com.rovertherapist.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.helper.AndroidConstants;
import com.helper.HttpView;
import com.helper.UserModel;

public class RegisterActivity extends CommonActivity {
	EditText et, et1, et2, et3, et4;
	AutoCompleteTextView autoCompleteLocation;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		et = (EditText) findViewById(R.id.editText);
		et1 = (EditText) findViewById(R.id.editText1);

		et3 = (EditText) findViewById(R.id.editText3);
		et4 = (EditText) findViewById(R.id.editText4);

	}

	public void register(View v) {

		String url = AndroidConstants.MAIN_URL()
				+ "method=insertUserDetailsObject";
		String query = "displayname=" + et.getText() + "&useremail="
				+ et1.getText() + "&userpass=" + et3.getText() + "&imei="
				+ getIMEI();
		url += "&" + query;
		AndroidConstants.EMAIL_ID = et1.getText().toString();
		UserModel result = (UserModel) HttpView.connect2ServerObject(url);
		if (result.isSuccess()) {
			System.out.println("Calling Register User "
					+ et.getText().toString() + " " + et1.getText().toString());
			go(WelcomeActivity.class);
		} else {
			toast("User already exists");
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

}
