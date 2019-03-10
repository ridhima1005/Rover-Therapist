package com.rovertherapist.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.entities.DayEntity;
import com.entities.PlaceEntity;
import com.entities.ReviewEntity;
import com.helper.StringHelper;

public class Place extends Activity {
	TextView placeName = null;
	TextView placeAdd = null;
	TextView placePhoneNumber = null;
	ImageView imageView = null;
	RatingBar ratingBar = null;
	TextView url = null;
	TextView txtreview = null;
	TextView txtopeningsHRS = null;
	TextView clodesMSG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		placeName = (TextView) findViewById(R.id.textViewPlaceName);
		placeAdd = (TextView) findViewById(R.id.textViewPlaceAddress);
		placePhoneNumber = (TextView) findViewById(R.id.textViewPhoneNumber);
		imageView = (ImageView) findViewById(R.id.serverImageView);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		url = (TextView) findViewById(R.id.textViewURL);
		clodesMSG = (TextView) findViewById(R.id.textViewclosedMSG);

		txtreview = (TextView) findViewById(R.id.textView6);
		txtopeningsHRS = (TextView) findViewById(R.id.textView8);

		PlaceEntity placeEntity = (PlaceEntity) getIntent()
				.getSerializableExtra("place");

		placeName.setText(placeEntity.getDetailEntity().getName());
		placeAdd.setText(placeEntity.getDetailEntity().getFormatted_address());
		String ph = placeEntity.getDetailEntity().getFormatted_phone_number();
		if (ph == null || ph.equals("")) {
			placePhoneNumber.setText("No Data found");
		} else {
			placePhoneNumber.setText(ph);
		}
		String urlstr = placeEntity.getDetailEntity().getUrl();
		if (urlstr == null || urlstr.equals("")) {
			url.setText("No Data found");
		} else {
			url.setText(Html.fromHtml("<a href=\"" + urlstr + "\">" + urlstr
					+ "</a>"));
			url.setMovementMethod(LinkMovementMethod.getInstance());

		}

		boolean open = placeEntity.getDetailEntity().isOpen_now();
		if (!open) {
			imageView.setImageResource(R.drawable.closed_sign);
			clodesMSG
					.setText("Closed can also mean there is no data for this field !");
		} else {
			imageView.setImageResource(R.drawable.open_sign);
			clodesMSG.setText("");
		}

		Integer rating = placeEntity.getDetailEntity().getUser_ratings();
		if (rating == null) {
			rating = 0;
		}
		ratingBar.setEnabled(false);
		ratingBar.setStepSize(0.5f);
		ratingBar.setNumStars(5);
		ratingBar.setMax(5);

		ratingBar.setRating(rating);

		DayEntity[] dayEntities = placeEntity.getDetailEntity()
				.getOpening_HRS();
		String openingHRSData = "";
		if (dayEntities != null && dayEntities.length > 0) {
			for (DayEntity dayEntity : dayEntities) {
				try {
					openingHRSData = openingHRSData
							+ StringHelper.getDay(dayEntity.getDay())
							+ " \r\n Open Hrs :  " + dayEntity.getOpenHRS()
							+ " \r\n Close Hrs:  " + dayEntity.getCloseHRS();
					openingHRSData = openingHRSData
							+ "\r\n--------------------------------------------------------------------\r\n";
				} catch (Exception e) {

				}
			}
			txtopeningsHRS.setText(openingHRSData);
		} else if (openingHRSData.equals("")) {
			txtopeningsHRS.setText("No Data found");
		}

		ReviewEntity[] entities = placeEntity.getDetailEntity().getReviews();
		String reviewsData = "";
		if (entities != null && entities.length > 0) {
			for (ReviewEntity entity : entities) {
				reviewsData = reviewsData + "Author :" + entity.getAuthorName();
				reviewsData = reviewsData + "\r\nReview :" + entity.getText();
				reviewsData = reviewsData
						+ "\r\n--------------------------------------------------------------------\r\n";
			}
			txtreview.setText(reviewsData);
		} else {
			txtreview.setText("No Data found");
		}

	}

	public void callNumber(View view) {
		String phone_no = placePhoneNumber.getText().toString()
				.replaceAll("-", "");
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phone_no));
		startActivity(callIntent);
	}

}
