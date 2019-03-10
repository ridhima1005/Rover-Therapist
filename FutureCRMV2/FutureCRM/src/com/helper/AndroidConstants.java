/*
 * AndroidConstants.java contains all the constants which are needed at the time of 
 * Connecting to server via Android Application (IP address of server,Port number of server etc.)
 * 
 * It also contains the variables which are null by default but when user registers 
 * for the first time all the values will be stored into the database (Email ID, IMEI number, Name of the User)
 * and those values will be displayed in web application which will run on server side
 * 
 * It also contains Complaint Number which is needed when user Lodge a Complaint SMS 
 * will be delivered to that number. Google Maps API key also provided.
 */

package com.helper;

import android.os.Environment;

public class AndroidConstants {
	public AndroidConstants() {
		// TODO Auto-generated constructor stub
	}

	public static String MOBILE_IP_ADDRESS = "";
	public static String SELECTED_MACHINE_NAME = "";
	public static String SELECTED_MACHINE_IP = "";

	public static String MAIN_SERVER_IP = "192.168.0.101";
	public static String MAIN_SERVER_PORT = "8080";

	public static final String MAIN_URL() {
		return "http://" + AndroidConstants.MAIN_SERVER_IP + ":"
				+ AndroidConstants.MAIN_SERVER_PORT
				+ "/TouristCRMServer/pages/tiles/ajax.jsp?phone=1&";
	}

	public static final int ANDROID_MESSAGE_LISTENER = 4223;
	public static final long ANDROID_METHOD_TIMEOUT = 60 * 1000;
	public static String EMAIL_ID = "";
	public static String IMEI = "";
	public static String DISPLAY_NAME = "";
	public static String COMPLAINT_NUMBER = "+918087849334";

	public static UserModel currentUser = null;
	// public static final String BROWSER_API_KEY
	// ="AIzaSyAX3IoLh-z58Ow08J6TN3giAGKxFIVlqag";// BROWSER KEY ACCOUNT 1

	public static final String BROWSER_API_KEY = "AIzaSyBFbRU97WqVexHe7EoFVA3nQvxQAEuqTYk"; // BROWSER
																							// KEY
																							// ACCOUNT
																							// 2

	// public static final String
	// BROWSER_API_KEY="AIzaSyBzsoV3D0Qurspxu2jJcwR_NH9rauTG-l8";

	// **public static final String
	// BROWSER_API_KEY="AIzaSyAlZpJxaZTY1tzha-YxmutUa6bzCen9CsY";
	public static final String BASE_FILE_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/attractions/";
}
