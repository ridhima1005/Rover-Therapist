/*
 * DayEntity.java is used to show the information about places 
 * such as opening hour of that place and closing hours.
 * 
 */

package com.entities;

import java.io.Serializable;

public class DayEntity implements Serializable {
	private int day;
	private int openHRS;
	private int closeHRS;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getOpenHRS() {
		return openHRS;
	}

	public void setOpenHRS(int openHRS) {
		this.openHRS = openHRS;
	}

	public int getCloseHRS() {
		return closeHRS;
	}

	public void setCloseHRS(int closeHRS) {
		this.closeHRS = closeHRS;
	}

}
