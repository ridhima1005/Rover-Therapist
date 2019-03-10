package map.helper;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class GPSModel implements Serializable {
	static final long serialVersionUID = 112121L;
	double lat, lng;
	String address;
	String title, desc;
	Bitmap icon = null;

	public GPSModel() {
		// TODO Auto-generated constructor stub
	}

	public GPSModel(double lat, double lng, String address, String title,
			String desc, Bitmap b) {
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.title = title;
		this.desc = desc;
		this.icon = b;

	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

}
