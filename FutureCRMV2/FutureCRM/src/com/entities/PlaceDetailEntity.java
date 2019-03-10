/*
 * PlaceDetailEntity.java is used to show information about the places such as Name of the Place,
 * Address of the Place, User Ratings, Reviews, Phone Number, URL and Type of that Place (Cafe, Church, Airport etc.)
 */
package com.entities;

import java.io.Serializable;

public class PlaceDetailEntity implements Serializable {

	private String name;
	private boolean open_now;
	private String formatted_address;
	private DayEntity[] opening_HRS;

	private Integer user_ratings;
	private ReviewEntity[] reviews;
	private String formatted_phone_number;
	private String url;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFormatted_phone_number() {
		return formatted_phone_number;
	}

	public void setFormatted_phone_number(String formatted_phone_number) {
		this.formatted_phone_number = formatted_phone_number;
	}

	public ReviewEntity[] getReviews() {
		return reviews;
	}

	public void setReviews(ReviewEntity[] reviews) {
		this.reviews = reviews;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen_now() {
		return open_now;
	}

	public void setOpen_now(boolean open_now) {
		this.open_now = open_now;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public DayEntity[] getOpening_HRS() {
		return opening_HRS;
	}

	public void setOpening_HRS(DayEntity[] opening_HRS) {
		this.opening_HRS = opening_HRS;
	}

	public Integer getUser_ratings() {
		return user_ratings;
	}

	public void setUser_ratings(Integer integer) {
		this.user_ratings = integer;
	}

}
