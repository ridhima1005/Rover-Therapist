package com.constants;

public class ServerConstants {

	// JDBC Connectivity
	public static final String db_driver = "com.mysql.jdbc.Driver";
	public static final String db_user = "root";
	public static final String db_pwd = "";
	public static final String db_url = "jdbc:mysql://localhost/touristcrm";

	// Application Titles
	public static final String APP_TITLE = "ROVER THERAPIST";
	public static final String APP_SLOGAN = "Tourist Buddy";

	// Pages
	public static final String PAGE_LOGIN = "pages/index.jsp";

	// Queries
	public static final String CHECK_LOGIN = "SELECT * FROM population.useraccount where login=? and pass=?";
	public static final String DOMAIN_QUERY = " SELECT * FROM `domain` ";
	public static final String INSERT_ADD = "INSERT INTO  domainInfo( domainId,review, description, phoneNumber,ratinguserName,rating,latitude,longitude,location) values (?,?,?,?,?,?,?,?,?)";
	public static final String SELECT_QUERY = "SELECT iddomainInfo, domainId, description, location, updatedDate, photourl, pincode, latitude, longitude, rating, ratinguserName, phoneNumber, review FROM `domaininfo` where domainid in (?)";
	public static final String Delete_Query = "delete  FROM domaininfo where iddomainInfo like ? ";
	public static final String Delete_User = "delete  FROM useraccount where imei like ?";

	// Role ID
	public static final String ADMIN = "1";
	public static final String USER = "2";

	// Server Constants For Server Hit From Android
	public static String MOBILE_IP_ADDRESS = "";
	public static String MY_HOST_NAME = "";
	public static String lookback_self = "127.0.0.1";
	public static final int ANDROID_MESSAGE_LISTENER = 4223;
	public static final float FRIEND_NOTIFICATION_KM_RANGE = 2f;
}
