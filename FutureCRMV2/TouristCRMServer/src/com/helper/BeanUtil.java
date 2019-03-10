package com.helper;

import java.io.Serializable;

public class BeanUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int key = 0;
	String value = "";

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
