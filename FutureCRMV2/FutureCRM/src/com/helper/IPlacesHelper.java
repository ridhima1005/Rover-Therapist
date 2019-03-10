package com.helper;

import java.util.List;

import com.entities.PlaceEntity;

public interface IPlacesHelper {
	public List<PlaceEntity> searchPlaces(String longitude, String latitude,
			String RADIUS, String TYPES);

}
