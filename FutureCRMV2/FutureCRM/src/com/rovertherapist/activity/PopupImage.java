package com.rovertherapist.activity;

import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.helper.AndroidConstants;

public class PopupImage {
	Context context;
	HashMap images;

	public PopupImage(Context mcontect, HashMap images) {
		this.context = mcontect;
		this.images = images;
	}

	int cnt = 0;

	public void showImage() {
		cnt = 0;

		System.out.println("images " + images);
		if (images.size() > 0) {
			try {
				LayoutInflater inflater = LayoutInflater.from(context);
				View dialogview = inflater.inflate(R.layout.help, null);

				// toast(name);
				// img.setBackground(R.drawable.)
				AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(
						context);
				// dialogbuilder.setTitle("Help");

				// dialogbuilder.setView(dialogview);

				final AlertDialog dialogDetails = dialogbuilder.create();
				dialogDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialogDetails.setView(dialogview, 5, 5, 5, 5);

				final ImageView img = (ImageView) dialogview
						.findViewById(R.id.placeImageView);
				img.setOnTouchListener(new ImageView.OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						ImageView view = (ImageView) v;
						view.setScaleType(ImageView.ScaleType.MATRIX);
						float scale;

						// Dump touch event to log
						// dumpEvent(event);

						// Handle touch events here...
						switch (event.getAction() & MotionEvent.ACTION_MASK) {

						case MotionEvent.ACTION_DOWN: // first finger down only
							savedMatrix.set(matrix);
							start.set(event.getX(), event.getY());
							Log.d(TAG, "mode=DRAG");
							mode = DRAG;
							break;
						case MotionEvent.ACTION_UP: // first finger lifted
						case MotionEvent.ACTION_POINTER_UP: // second finger
															// lifted
							mode = NONE;
							Log.d(TAG, "mode=NONE");
							break;
						case MotionEvent.ACTION_POINTER_DOWN: // second finger
																// down
							oldDist = spacing(event);
							Log.d(TAG, "oldDist=" + oldDist);
							if (oldDist > 5f) {
								savedMatrix.set(matrix);
								midPoint(mid, event);
								mode = ZOOM;
								Log.d(TAG, "mode=ZOOM");
							}
							break;

						case MotionEvent.ACTION_MOVE:
							if (mode == DRAG) { // movement of first finger
								matrix.set(savedMatrix);
								if (view.getLeft() >= -392) {
									matrix.postTranslate(
											event.getX() - start.x,
											event.getY() - start.y);
								}
							} else if (mode == ZOOM) { // pinch zooming
								float newDist = spacing(event);
								Log.d(TAG, "newDist=" + newDist);
								if (newDist > 5f) {
									matrix.set(savedMatrix);
									scale = newDist / oldDist;
									matrix.postScale(scale, scale, mid.x, mid.y);
								}
							}
							break;
						}
						// Perform the transformation
						view.setImageMatrix(matrix);
						return true;
					}
				});
				img.setScaleType(ScaleType.FIT_XY);

				String imageInSD = AndroidConstants.BASE_FILE_PATH
						+ images.get(cnt);
				System.out.println("name!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
						+ imageInSD);

				Bitmap bm = BitmapFactory.decodeFile(imageInSD);
				if (bm == null)
					dialogDetails.hide();
				else
					System.out.println("Exist name!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
							+ imageInSD);
				img.setImageBitmap(bm);
				if (images != null && images.size() == 1) {
					dialogview.findViewById(R.id.back).setVisibility(
							Button.INVISIBLE);
					dialogview.findViewById(R.id.forward).setVisibility(
							Button.INVISIBLE);
				} else {
					dialogview.findViewById(R.id.back).bringToFront();
					dialogview.findViewById(R.id.forward).bringToFront();
				}
				dialogview.findViewById(R.id.back).setOnClickListener(
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								cnt--;
								if (cnt >= 0) {
									String imageInSD = AndroidConstants.BASE_FILE_PATH
											+ images.get(cnt);
									System.out
											.println("name!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
													+ imageInSD
													+ "   cnt "
													+ cnt);
									Bitmap bm = BitmapFactory
											.decodeFile(imageInSD);
									if (bm != null)
										img.setImageBitmap(bm);
								}
							}
						});
				dialogview.findViewById(R.id.forward).setOnClickListener(
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								cnt++;

								if (cnt < images.size()) {
									String imageInSD = AndroidConstants.BASE_FILE_PATH
											+ images.get(cnt);
									System.out
											.println("name!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
													+ imageInSD
													+ "   cnt "
													+ cnt);
									Bitmap bm = BitmapFactory
											.decodeFile(imageInSD);
									if (bm != null)
										img.setImageBitmap(bm);
								}
							}
						});

				dialogview.findViewById(R.id.button1).setOnClickListener(
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialogDetails.hide();
							}
						});
				dialogDetails.show();
				matrix.postScale(3.5f, 3.5f, mid.x, mid.y);
				img.setImageMatrix(matrix);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	String TAG = "PopupMenu";
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;
	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
}
