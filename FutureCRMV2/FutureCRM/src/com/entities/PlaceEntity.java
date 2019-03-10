/*
 * 
 */

package com.entities;

import java.io.Serializable;

public class PlaceEntity implements Serializable {

	private static final long serialVersionUID = 5091210480565441609L;

	private boolean isServer;

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

	private double latitude;
	private double longitude;
	private String id;
	private String reference;
	private int distance;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	private PlaceDetailEntity detailEntity;

	public PlaceDetailEntity getDetailEntity() {
		return detailEntity;
	}

	public void setDetailEntity(PlaceDetailEntity detailEntity) {
		this.detailEntity = detailEntity;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
