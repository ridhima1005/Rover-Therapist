package com.entities;

import java.io.Serializable;

public class TypeEntity implements Serializable {

	String name = null;
	boolean selected = false;

	public TypeEntity(String name, boolean selected) {
		super();

		this.name = name;
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
