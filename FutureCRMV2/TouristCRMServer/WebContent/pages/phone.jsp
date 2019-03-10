<%@page
	import="sun.reflect.ReflectionFactory.GetReflectionFactoryAction"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="com.util.*"%>
<%@page import="com.util.ConnectionManager"%>
<%@page import="com.helper.*"%>
<%@page trimDirectiveWhitespaces="true"%>

<%
	try {
		String sMethod = StringHelperNew.n2s(request
				.getParameter("method"));
		System.out.println("sMethod " + sMethod);
		StringBuffer sb = new StringBuffer("");
		boolean skipWriting = false;
		java.util.HashMap<String, String> parameters = StringHelperNew
				.displayRequest(request);
		System.out.println("URL Got = " + request.getRequestURL());
		OutputStream responseBody = response.getOutputStream();

		if (sMethod.equalsIgnoreCase("img")) {
			BufferedImage buffer = ImageIO.read(new File(
					"E:/bankingsite.jpg"));
			ImageIO.write(buffer, "jpg", responseBody);
			responseBody.flush();
			responseBody.close();
			skipWriting = true;
		} else if (sMethod.equalsIgnoreCase("checkIMEI")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			boolean success = ConnectionManager.isIMEIExists(imei);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);
		} else if (sMethod.equalsIgnoreCase("checkParentPhone")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			String incomingno = StringHelperNew.n2s(parameters
					.get("incomingno"));
			boolean success = ConnectionManager.isPhnoExists(imei,
					incomingno);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);
		} else if (sMethod.equalsIgnoreCase("getLocationUsers")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			List lost = ConnectionManager.getSameLocationUserList(imei);
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(lost);
			os.flush();
			os.close();
			skipWriting = true;
			System.out.println("SkipWriting Return " + skipWriting);
		} else if (sMethod.equalsIgnoreCase("getUserPreference")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			int success = ConnectionManager.getDomainId(imei);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);
		} else if (sMethod.equalsIgnoreCase("saveGeoTags")) {
			String success = ConnectionManager.saveGeoTags(parameters);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);
		} else if (sMethod.equalsIgnoreCase("getUserDate")) {
			String str = StringHelperNew.n2s(parameters.get("lat"));
			String date = ConnectionManager.getUserDate(str);
			System.out
					.println("Connection Manager Return Date " + date);
			sb.append(date);
		} else if (sMethod.equalsIgnoreCase("saveMsg")) {
			String success = ConnectionManager.saveMsg(parameters);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);
		} else if (sMethod.equalsIgnoreCase("getUserGeoTag")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			List param = ConnectionManager.getUserGeoTag(imei);
			System.out.println("Connection Manager Return List "
					+ param);
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(param);
			os.flush();
			os.close();
			skipWriting = true;
		} else if (sMethod.equalsIgnoreCase("getFriendLocation")) {
			//String imei=StringHelper.n2s(parameters.get("imei"));
			List param = ConnectionManager.getFriendLocation();
			System.out.println("Connection Manager Return List "
					+ param);
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(param);
			os.flush();
			os.close();
			skipWriting = true;
		} else if (sMethod.equalsIgnoreCase("saveUser")) {
			String success = ConnectionManager.saveUser(parameters);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);

		} else if (sMethod.equalsIgnoreCase("getfrnd")) {
			String name = StringHelperNew.n2s(parameters.get("name"));
			List param = ConnectionManager.getfrnd(name);
			System.out.println("Connection Manager Return List "
					+ param);
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(param);
			os.flush();
			os.close();
			skipWriting = true;
		} else if (sMethod.equalsIgnoreCase("insertTrackUser")) {
			boolean success = ConnectionManager
					.insertTrackUser(parameters);
			System.out.println("Connection Manager Return " + success);
			sb.append(success);
		} else if (sMethod.equalsIgnoreCase("getUserLocationDetails")) {
			String name = StringHelperNew.n2s(parameters.get("name"));
			List param = ConnectionManager.getUserLocationDetails(name);
			System.out.println("Connection Manager Return List "
					+ param);
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(param);
			os.flush();
			os.close();
			skipWriting = true;
		} else if (sMethod.equalsIgnoreCase("getMyLocation")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			String success[] = ConnectionManager.getLocation(imei);
			System.out.println("Connection Manager Return With Array");
			sb.append(success[0] + "=" + success[1]);
		} else if (sMethod.equalsIgnoreCase("getUserDetails")) {
			String imei = StringHelperNew.n2s(parameters.get("imei"));
			List param = ConnectionManager.getUserDetails(imei);
			System.out.println("Connection Manager Return List "
					+ param);
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(param);
			os.flush();
			os.close();
			skipWriting = true;
		} else if (sMethod.equalsIgnoreCase("getGPSLocation")) {
			String lat = StringHelperNew.n2s(parameters.get("lat"));
			String longitude = StringHelperNew.n2s(parameters
					.get("long"));
			String address[] = ReverseGeocoder.getLocation(lat + ","
					+ longitude);
			sb.append(address[0] + "=" + address[1]);
		} else if (sMethod.equalsIgnoreCase("domainList")
				|| sMethod.equalsIgnoreCase("domainInfo")
				|| sMethod.equalsIgnoreCase("getDomainListMap")) {
			System.out.println("Get Domain List ");
			List list = null;
			if (sMethod.equalsIgnoreCase("domainList"))
				list = ConnectionManager.getDomainList();
			else if (sMethod.equalsIgnoreCase("domainInfo")) {
				String pincode = StringHelperNew.n2s(parameters
						.get("pincode"));
				list = ConnectionManager.getDomainInfo(pincode);
			} else if (sMethod.equalsIgnoreCase("getDomainListMap")) {
				list = ConnectionManager.getDomainListMap();
			}
			ObjectOutputStream os = new ObjectOutputStream(responseBody);
			os.writeObject(list);
			os.flush();
			os.close();
			skipWriting = true;
		}
		System.out.println("Result " + sb.toString());
		if (!skipWriting) {
			responseBody.write(sb.toString().getBytes());
			responseBody.flush();
			responseBody.close();
		}
	} catch (Exception e) {
		System.out
				.println("--------------Entering Catch Exception-----------------");
		e.printStackTrace();
		System.out
				.println("----------------------------------------------------------------");
	}
%>