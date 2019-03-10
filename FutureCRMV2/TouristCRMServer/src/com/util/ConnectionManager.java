/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.constants.ServerConstants;
import com.entities.PlaceDetailEntity;
import com.entities.PlaceEntity;
import com.entities.ReviewEntity;
import com.helper.DBUtils;
import com.helper.DomainInfo;
import com.helper.StringHelperNew;

public class ConnectionManager extends com.util.WebConnectionManager {
	
	public static boolean isIMEIExists(String imei) {
		String query = "SELECT imei, ipaddress, domainId, displayName, lac, cellId, lat, longitude, activeFlag, udate, userid FROM useraccount where imei = '"
				+ imei + "'";
		System.out.println("query " + query);

		List list = getMapList(query);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static List getfrnd(String name) {
		String query = "SELECT  lat,longitude  FROM useraccount where displayName like '%"
				+ name + "%'";
		System.out.println("query " + query);
		List list = getMapList(query);
		for (int i = 0; i < list.size(); i++) {
			HashMap param = (HashMap) list.get(i);
			String lat = StringHelperNew.n2s(param.get("lat"));
			String longitude = StringHelperNew.n2s(param.get("longitude"));
			param.put("lat", lat);
			param.put("longitude", longitude);
		}

		return list;

	}

	public static String getUserDate(String lat) {
		String query = "SELECT udate FROM `trackuser` where lat='" + lat + "'";
		System.out.println("query " + query);
		List list = getMapList(query);
		if (list.size() > 0) {
			HashMap param = (HashMap) list.get(0);
			lat = StringHelperNew.n2s(param.get("udate"));
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>lat .." + lat);
		}
		return lat;

		// TODO Auto-generated method stub

	}

	public static boolean isPhnoExists(String imei, String incomingno) {
		String query = "SELECT *  FROM useraccount where imei  like '%" + imei
				+ "%' and fathercontacts like '%" + incomingno + "%'";
		System.out.println("query " + query);

		List list = getMapList(query);
		System.out.println("i n isPhnoExists <<<<<<<<<<<<<..");
		if (list.size() > 0) {

			System.out.println("i n isPhnoExists <<<<<<<<<<<<<..");
			return true;
		} else {
			System.out.println("i n isPhno not Exists <<<<<<<<<<<<<..");
			return false;
		}
	}

	public static String[] getLocation(String imei) {
		String address[] = new String[2];
		String query = "SELECT  lac, cellId, lat, longitude  FROM useraccount where imei = '"
				+ imei + "'";
		System.out.println("query " + query);
		List list = getMapList(query);

		if (list.size() > 0) {

			HashMap param = (HashMap) list.get(0);
			String lat = StringHelperNew.n2s(param.get("lat"));
			String longitude = StringHelperNew.n2s(param.get("longitude"));
			address = ReverseGeocoder.getLocation(lat + "," + longitude);
			return address;
		} else {
			return address;
		}
	}

	public static List getDomainList() {
		String query = "SELECT domainId as `key`, domainDesc as value FROM domain";
		System.out.println("query " + query);
		List list = getBeanList(BeanUtil.class, query);
		return list;

	}

	public static List getDomainListMap() {
		String query = "SELECT domainId as `key`, domainDesc as value FROM domain";
		System.out.println("query " + query);
		List list = getMapList(query);

		return list;

	}

	public static List getDomainInfo(String location) {
		if (location.trim().length() == 0) {
			location = "411038";
		}
		// location="411038";
		String query = "SELECT domainId, description, location, date_format(updatedDate, '%a %d-%b-%y') as  updatedDate,photourl FROM domaininfo where pincode like '%"
				+ location + "%'order by domainId";
		System.out.println("query " + query);
		List list = getMapList(query);
		int domainId = 2;
		for (int i = 0; i < list.size(); i++) {
			HashMap param = (HashMap) list.get(i);
			int did = StringHelperNew.n2i(param.get("domainId"));
			if (did == domainId && i != 0) {
				list.remove(i);
				list.add(0, param);
			}

		}

		return list;

	}

	public static int getUserId(String imei) {
		String query = "SELECT  userid FROM useraccount where imei like '"
				+ imei + "'";
		System.out.println("query " + query);
		int userId = 0;
		List list = getMapList(query);
		try {
			HashMap param = (HashMap) list.get(0);
			userId = StringHelperNew.n2i(param.get("userid"));
		} catch (Exception e) {

		}
		return userId;

	}

	public static int getDomainId(String imei) {
		String query = "SELECT  domainId+1 as domainId FROM useraccount where imei = '"
				+ imei + "'";
		System.out.println("query " + query);

		List list = getMapList(query);
		HashMap param = (HashMap) list.get(0);
		int domainId = StringHelperNew.n2i(param.get("domainId"));
		return domainId;

	}

	public static List getUserDetails(String imei) {
		String query = "SELECT  * FROM useraccount where imei = '" + imei + "'";
		System.out.println("query " + query);
		List list = getMapList(query);

		return list;

	}

	public static List getUserGeoTag(String imei) {
		String query = "SELECT  * FROM usergeotags where imei   like  '" + imei
				+ "'";
		System.out.println("query  from table usergeotags " + query);
		List list = getMapList(query);

		return list;

	}

	public static List getFriendLocation() {
		String query = "SELECT `displayName`, `lat`, `longitude` FROM `useraccount`";
		System.out.println("query  from table useraccout " + query);
		List list = getMapList(query);
		System.out.println("Data..." + list);
		return list;

	}

	public static List getUserLocationDetails(String name) {
		String query = "SELECT  lat,longitude,date_format(udate, '%a %d-%b-%y') as  updatedDate,photourl,displayName FROM useraccount where mailid like '%"
				+ name + "%' OR displayName like '%" + name + "%'";
		System.out.println("query " + query);
		List list = getMapList(query);
		for (int i = 0; i < list.size(); i++) {
			HashMap param = (HashMap) list.get(i);
			String lat = StringHelperNew.n2s(param.get("lat"));
			String longitude = StringHelperNew.n2s(param.get("longitude"));
			String location[] = ReverseGeocoder.getLocation(lat + ","
					+ longitude);

			param.put("ADDRESS", location[0]);
			param.put("PINCODE", location[1]);
		}
		return list;
	}

	public static boolean getDisplayName(String displayName) {
		String query = "SELECT  userid FROM useraccount where displayName = '"
				+ displayName + "'";
		System.out.println("query " + query);

		List list = getMapList(query);
		if (list.size() > 0)
			return true;
		else
			return false;

	}

	public static String saveMsg(HashMap parameters) {
		String phoneno = StringHelperNew.n2s(parameters.get("phoneno"));
		String msg = StringHelperNew.n2s(parameters.get("smsbody"));

		String query = "insert into smsmaneger (phoneno,  msg) values(?,?)";
		int i = executeUpdate(query, new Object[] { phoneno, msg });
		if (i > 0) {
			System.out.println("i n save user Msg<<<<<<<<<<<<<..");
			return "success";
		} else {
			System.out.println("i n save user Msg< <failure<<<<<<<<<<..");
			return "failure";
		}

	}

	public static String saveGeoTags(HashMap parameters) {

		String geoTagName = StringHelperNew.n2s(parameters.get("geoTagName"));

		String preference = StringHelperNew.n2s(parameters.get("preference"));
		int por = 0;
		if (!preference.equalsIgnoreCase("Profile"))
			por = 1;
		String actiondesc = StringHelperNew.n2s(parameters.get("actiondesc"));
		String lat = StringHelperNew.n2s(parameters.get("lat"));
		String lng = StringHelperNew.n2s(parameters.get("lng"));
		String imei = StringHelperNew.n2s(parameters.get("imei"));
		String query = "insert into usergeotags (geoTagName,  preference, actiondesc, lat, lng,imei) values(?,?,?,?,?,?)";
		int i = executeUpdate(query, new Object[] { geoTagName, por,
				actiondesc, lat, lng, imei });
		System.out.println("Test Statement for geotag" + i);
		if (i > 0) {
			System.out.println("i m in geotag success mmmmmmmmmmmmmmmmmmm");
			return "success";
		} else {
			System.out.println("i m in geotag  failure   mmmmmmmmmmmmmmmmmmm");
			return "failure";
		}
	}

	public static String saveUser(HashMap parameters) {
		String message = "";
		String imei = StringHelperNew.n2s(parameters.get("imei"));
		String ipaddress = StringHelperNew.n2s(parameters.get("ipaddress"));
		int domainId = StringHelperNew.n2i(parameters.get("domainId"));

		String displayName = StringHelperNew.n2s(parameters.get("displayName"));
		String lac = StringHelperNew.n2s(parameters.get("lac"));
		String cellId = StringHelperNew.n2s(parameters.get("cellId"));
		String lat = StringHelperNew.n2s(parameters.get("lat"));
		String longitude = StringHelperNew.n2s(parameters.get("longitude"));
		String mailid = StringHelperNew.n2s(parameters.get("mailid"));
		String phoneno = StringHelperNew.n2s(parameters.get("phoneno"));

		boolean isUserExist = isIMEIExists(imei);
		String query = "";
		if (!isUserExist) {
			if (!getDisplayName(displayName)) {

				query = "insert into useraccount (imei, ipaddress, domainId, displayName, lac, cellId, lat, longitude,mailid, phoneno ) values ('"
						+ imei
						+ "','"
						+ ipaddress
						+ "',"
						+ domainId
						+ ",'"
						+ displayName
						+ "','"
						+ lac
						+ "','"
						+ cellId
						+ "','"
						+ lat
						+ "','"
						+ longitude
						+ "','"
						+ mailid
						+ "','"
						+ phoneno + "')";
				message = "Success";
			} else {
				message = "Display name already exists";

			}
		} else {

			query = "update useraccount set ipaddress='" + ipaddress
					+ "',domainId=" + domainId + ",mailid='" + mailid
					+ "',phoneno='" + phoneno + "'" + ",displayName='"
					+ displayName + "',lac='" + lac + "',cellId='" + cellId
					+ "',lat='" + lat + "',longitude='" + longitude
					+ "' where imei='" + imei + "'";
			message = "Success";
		}
		System.out.println("query " + query);
		int ret = executeUpdate(query, null);

		return message;

	}

	public static List getSameLocationUserList(String imei) {
		int userid = getUserId(imei);

		String query = "SELECT imei, ipaddress, domainId, displayName, lac, cellId, lat, longitude, activeFlag, udate, userid FROM useraccount where userid = "
				+ userid;
		System.out.println("query " + query);

		List list = getMapList(query);
		String curr_lat = ((HashMap) list.get(0)).get("lat").toString();
		String curr_longitude = ((HashMap) list.get(0)).get("longitude")
				.toString();
		query = "SELECT imei, ipaddress, domainId, displayName, lac, cellId, lat, longitude, activeFlag,date_format(udate, '%d-%b-%y %a') as udate , userid FROM useraccount where userid <> "
				+ userid;
		list = getMapList(query);
		double currlat = new Double(curr_lat).doubleValue();
		double currlon = new Double(curr_longitude).doubleValue();

		List result = new ArrayList();
		// for (int i=0;i<list.size();i++) {
		// HashMap param=(HashMap)list.get(i);
		// String new_lat=param.get("lat").toString();
		// String new_longitude=param.get("longitude").toString();
		// double newlat=new Double(new_lat).doubleValue();
		// double newlon=new Double(new_longitude).doubleValue();
		// double distance = calcDistance(currlat, currlon, newlat, newlon);
		// System.out.println("Distance is "+distance);
		// if(distance<ServerConstants.FRIEND_NOTIFICATION_KM_RANGE){
		// String[] ar=ReverseGeocoder.getLocation(new_lat+","+new_longitude);
		// param.put("ADDR", ar[0]);
		// result.add(param);
		// }
		//
		// }

		result = list;

		return result;

	}

	public static double calcDistance(double lat_a, double lng_a, double lat_b,
			double lng_b) {
		float pk = (float) (180 / Math.PI);

		double a1 = lat_a / pk;
		double a2 = lng_a / pk;
		double b1 = lat_b / pk;
		double b2 = lng_b / pk;

		double t1 = (double) (Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math
				.cos(b2));
		double t2 = (double) (Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math
				.sin(b2));
		double t3 = (double) (Math.sin(a1) * Math.sin(b1));
		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
	}

	public static boolean insertTrackUser(HashMap parameters) {
		String imei = StringHelperNew.n2s(parameters.get("imei"));
		int userId = getUserId(imei);
		String lac = StringHelperNew.n2s(parameters.get("lac"));
		String cellId = StringHelperNew.n2s(parameters.get("cellId"));
		String lat = StringHelperNew.n2s(parameters.get("lat"));
		String longitude = StringHelperNew.n2s(parameters.get("longitude"));
		boolean isUserExist = isIMEIExists(imei);
		String query = "";
		if (isUserExist) {
			String query1 = "update useraccount set lac='" + lac + "',cellId='"
					+ cellId + "',lat='" + lat + "',longitude='" + longitude
					+ "' where imei='" + imei + "'";
			int ret1 = executeUpdate(query1, null);
			query = "insert into trackuser (userid, lat, longitude, cellid, lac) values ("
					+ userId
					+ ",'"
					+ lat
					+ "','"
					+ longitude
					+ "','"
					+ cellId
					+ "','" + lac + "')";
		}
		System.out.println("query " + query);
		int ret = executeUpdate(query, null);
		if (ret > 0)
			return true;
		else
			return false;

	}

	public static boolean insertUser(HashMap parameters) {
		String imei = StringHelperNew.n2s(parameters.get("imei"));
		String displayname = StringHelperNew.n2s(parameters.get("displayname"));
		String useremail = StringHelperNew.n2s(parameters.get("useremail"));
		String userpass = StringHelperNew.n2s(parameters.get("userpass"));
		String query = "insert into useraccount 	 "
				+ "(password, imei,displayName, activeFlag,mailid) values (?,?,?,?,?)";
		System.out.println("query " + query);
		int ret = executeUpdate(query, userpass, imei, displayname, "Y",
				useremail);
		if (ret > 0)
			return true;
		else
			return false;

	}

	public static boolean getData(String query) {
		boolean success = false;
		String userdata = null;
		Connection con = null;
		Statement s = null;
		try {
			con = getDBConnection();
			// try and create a java.sql.Statement so we can run queries
			s = con.createStatement();
			s.execute(query); // select
			ResultSet rs = s.getResultSet(); // get any ResultSet that came from
												// our query

			if (rs != null)
				while (rs.next()) {

					success = true;

				}

		} catch (Exception err) {
			System.out.println("ERROR: " + err);
		} finally {
			try {
				s.close();
			} catch (SQLException e1) {

				e1.printStackTrace();
			} // close the Statement to let the database know we're done with it
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} // close the Connection to let the database know we're done with
				// it
		}
		return success;
	}

	public static List getBeanList(Class cls, String query) {
		Connection conn = null;
		List beans = null;
		try {
			conn = getDBConnection();

			QueryRunner qRunner = new QueryRunner();
			beans = (List) qRunner.query(conn, query, new BeanListHandler(cls));

		} catch (SQLException e) {
			// handle the exception
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return beans;
	}

	public static List getMapList(String query) {
		Connection conn = null;
		List beans = null;
		try {
			conn = getDBConnection();
			System.out.println("Executing " + query);
			QueryRunner qRunner = new QueryRunner();
			beans = (List) qRunner.query(conn, query, new MapListHandler());

		} catch (SQLException e) {
			// handle the exception
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return beans;
	}

	public static List getParameterizedList(String query, Object... param) {
		Connection conn = null;
		List beans = null;
		try {
			conn = getDBConnection();

			QueryRunner qRunner = new QueryRunner();
			beans = (List) qRunner.query(conn, query, new MapListHandler(),
					param);

		} catch (SQLException e) {
			// handle the exception
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return beans;
	}

	public static int executeUpdate(String query, Object... param) {
		Connection conn = null;
		int beans = 0;
		try {
			conn = getDBConnection();
			QueryRunner qRunner = new QueryRunner();
			if (param != null)
				beans = qRunner.update(conn, query, param);
			else
				beans = qRunner.update(conn, query);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return beans;
	}

	public static List<PlaceEntity> getPlace(HashMap parameters) {

		String types = StringHelperNew.n2s(parameters.get("types"));
		String distance = StringHelperNew.n2s(parameters.get("distance"));
		String latitude = StringHelperNew.n2s(parameters.get("latitude"));
		String longitude = StringHelperNew.n2s(parameters.get("longitude"));
		String typesQryStr = "";
		String[] typesArr = types.split("\\|");
		String domainIdList = "";
		for (int i = 0; i < typesArr.length; i++) {
			if (i == 1)
				typesQryStr = "'" + typesArr[i] + "'";
			else
				typesQryStr = typesQryStr + "," + "'" + typesArr[i] + "'";
		}
		if (typesQryStr.startsWith(",")) {
			typesQryStr = typesQryStr.substring(1);
		}
		String query = "SELECT domainId FROM domain where domainDesc in ("
				+ typesQryStr + ");";
		System.out.println("query " + query);
		List list = getMapList(query);
		for (int i = 0; i < list.size(); i++) {
			HashMap param = (HashMap) list.get(i);
			String domainId = StringHelperNew.n2s(param.get("domainId"));

			if (i == 1)
				domainIdList = "'" + domainId + "'";
			else
				domainIdList = domainIdList + "," + "'" + domainId + "'";

		}

		List<PlaceEntity> placeEntities = new ArrayList<PlaceEntity>();

		List<PlaceEntity> placeEntitiesFilterred = new ArrayList<PlaceEntity>();
		List<DomainInfo> PlacesList = (List<DomainInfo>) DBUtils.getBeanList(
				DomainInfo.class, ServerConstants.SELECT_QUERY,
				new Object[] { domainIdList });

		for (DomainInfo domainInfo : PlacesList) {

			PlaceEntity placeEntity = new PlaceEntity();
			PlaceDetailEntity placeDetailEntity = new PlaceDetailEntity();

			placeEntity
					.setLatitude(Double.parseDouble(domainInfo.getLatitude()));
			placeEntity.setLongitude(Double.parseDouble(domainInfo
					.getLongitude()));
			ReviewEntity[] reviewEntityArr = new ReviewEntity[1];
			ReviewEntity reviewEntity = new ReviewEntity();

			placeDetailEntity.setName(domainInfo.getDescription());
			placeDetailEntity.setFormatted_address(domainInfo.getLocation());
			placeDetailEntity.setFormatted_phone_number(domainInfo
					.getPhoneNumber());

			reviewEntity.setAuthorName(domainInfo.getRatinguserName());
			reviewEntity.setText(domainInfo.getReview());
			reviewEntityArr[0] = reviewEntity;
			placeDetailEntity.setReviews(reviewEntityArr);

			placeDetailEntity.setUser_ratings(Integer.parseInt(domainInfo
					.getRating()));

			placeEntity.setDetailEntity(placeDetailEntity);
			System.out.println("Place NAme "
					+ placeEntity.getDetailEntity().getName());
			placeEntities.add(placeEntity);

		}

		for (PlaceEntity placeEntity : placeEntities) {
			double inputDistance = Double.parseDouble(distance);
			double placeDistance = distance(placeEntity.getLatitude(),
					placeEntity.getLongitude(), Double.parseDouble(latitude),
					Double.parseDouble(longitude));
			if (placeDistance <= inputDistance / 1000) {
				System.out.println("Place NAme filter"
						+ placeEntity.getDetailEntity().getName());
				placeEntitiesFilterred.add(placeEntity);
			}

		}

		return placeEntities;

	}

	public static double distance(double lat1, double lon1, double lat2,
			double lon2) { // in KM
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist);
	}

	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
