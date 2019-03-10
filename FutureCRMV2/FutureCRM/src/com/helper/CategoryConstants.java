package com.helper;

//all categories of the places
public class CategoryConstants {

	// public static String
	// ATTRACTIONS="amusement_park|aquarium|art_gallery|bowling_alley|campground|casino|church|city_hall|embassy|establishment|hindu_temple|mosque|museum|night_club|park|place_of_worship|zoo";

	public static String ATTRACTIONS = "amusement_park|zoo|park";

	public static String[] typesArr = { "Attractions", "accounting", "airport",
			"amusement_park", "aquarium", "art_gallery", "atm", "bakery",
			"bank", "bar", "beauty_salon", "bicycle_store", "book_store",
			"bowling_alley", "bus_station", "cafe", "campground", "car_dealer",
			"car_rental", "car_repair", "car_wash", "casino", "cemetery",
			"church", "city_hall", "clothing_store", "convenience_store",
			"courthouse", "dentist", "department_store", "doctor",
			"electrician", "electronics_store", "embassy", "establishment",
			"finance", "fire_station", "florist", "food", "funeral_home",
			"furniture_store", "gas_station", "general_contractor",
			"grocery_or_supermarket", "gym", "hair_care", "hardware_store",
			"health", "hindu_temple", "home_goods_store", "hospital",
			"insurance_agency", "jewelry_store", "laundry", "lawyer",
			"library", "liquor_store", "local_government_office", "locksmith",
			"lodging", "meal_delivery", "meal_takeaway", "mosque",
			"movie_rental", "movie_theater", "moving_company", "museum",
			"night_club", "painter", "park", "parking", "pet_store",
			"pharmacy", "physiotherapist", "place_of_worship", "plumber",
			"police", "post_office", "real_estate_agency", "restaurant",
			"roofing_contractor", "school", "shoe_store", "shopping_mall",
			"spa", "stadium", "storage", "store", "subway_station",
			"synagogue", "taxi_stand", "train_station", "travel_agency",
			"university", "veterinary_care", "zoo" };
	public static String[][] weatherWiseClassfication = { // Morning=mor Evening
															// = eve Afternoon =
															// after
	{ "amusement_park", "mor", "hot cold cloudy" },
			{ "atm", "morn after eve", "hot cold cloudy" },
			{ "bakery", "morn after eve", "hot cold " },
			{ "bar", "eve", "hot cold" },
			{ "beauty_salon", "after ", "hot cold" }

	};

}
