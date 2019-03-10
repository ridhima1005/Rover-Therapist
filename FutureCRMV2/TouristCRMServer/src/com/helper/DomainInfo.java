package com.helper;

//domain information
public class DomainInfo {
	public DomainInfo(){
		
	}
	
	public DomainInfo(String iddomainInfo, String domainId, String description,
			String location, String updatedDate, String photourl,
			String pincode, String latitude, String longitude, String rating,
			String ratinguserName, String phoneNumber, String review) {
		super();
		this.iddomainInfo = iddomainInfo;
		this.domainId = domainId;
		this.description = description;
		this.location = location;
		this.updatedDate = updatedDate;
		this.photourl = photourl;
		this.pincode = pincode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.rating = rating;
		this.ratinguserName = ratinguserName;
		this.phoneNumber = phoneNumber;
		this.review = review;
	}

	public String iddomainInfo, domainId, description, location, updatedDate, photourl, pincode, latitude, longitude, rating, ratinguserName, phoneNumber, review="";

	public String getIddomainInfo() {
		return iddomainInfo;
	}

	public void setIddomainInfo(String iddomainInfo) {
		this.iddomainInfo = iddomainInfo;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRatinguserName() {
		return ratinguserName;
	}

	public void setRatinguserName(String ratinguserName) {
		this.ratinguserName = ratinguserName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	
	
}
