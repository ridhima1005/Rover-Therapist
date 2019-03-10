<%@ page trimDirectiveWhitespaces="true"%>
<%@page language="java" trimDirectiveWhitespaces="true"%>
<%@page import="com.entities.*"%>
<%@page import="com.helper.UserAccount"%>
<%@page import="com.helper.DomainInfo"%>
<%@page import="com.constants.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.util.ConnectionManager"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Enumeration"%>

<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.helper.DBUtils"%>
<%@page import="com.helper.StringHelperNew"%>
<%@page import="org.json.JSONTokener"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.*"%>
<%
	String method = StringHelperNew.n2s(request.getParameter("method"));
	String id = StringHelperNew.n2s(request.getParameter("id"));
	System.out.println(method + " " + id);
	java.util.HashMap parameters = com.helper.StringHelperNew
			.displayRequest(request);
	String str = "";
	OutputStream responseBody = response.getOutputStream();

	if (method.equalsIgnoreCase("getPlaces")) {
		System.out.println("In  Ajax getPlaces phone ");
		//List placesList=ConnectionManager.getPlace(parameters);

		String types = StringHelperNew.n2s(parameters.get("types"));
		String distance = StringHelperNew.n2s(parameters
				.get("distance"));
		String latitude = StringHelperNew.n2s(parameters
				.get("latitude"));
		String longitude = StringHelperNew.n2s(parameters
				.get("longitude"));
		String typesQryStr = "";
		String[] typesArr = types.split("\\|");
		String domainIdList = "";
		for (int i = 0; i < typesArr.length; i++) {
			if (i == 1)
				typesQryStr = "'" + typesArr[i] + "'";
			else
				typesQryStr = typesQryStr + "," + "'" + typesArr[i]
						+ "'";
		}
		if (typesQryStr.startsWith(",")) {
			typesQryStr = typesQryStr.substring(1);
		}
		String query = "SELECT domainId FROM domain where domainDesc in ("
				+ typesQryStr + ");";
		System.out.println("query " + query);
		List list = ConnectionManager.getMapList(query);
		for (int i = 0; i < list.size(); i++) {
			HashMap param = (HashMap) list.get(i);
			String domainId = StringHelperNew
					.n2s(param.get("domainId"));
			System.out.println("domainId " + domainId);
			if (i == 1)
				domainIdList = "'" + domainId + "'";
			else
				domainIdList = domainIdList + "," + "'" + domainId
						+ "'";

		}
		if (domainIdList.startsWith(",")) {
			domainIdList = domainIdList.substring(1);
		}
		System.out.println("domainIdList " + domainIdList);

		ArrayList placeEntities = new ArrayList();

		List<PlaceEntity> placeEntitiesFilterred = new ArrayList<PlaceEntity>();

		String placeQuery = ServerConstants.SELECT_QUERY;
		placeQuery = placeQuery.replace("?", domainIdList);
		List list1 = ConnectionManager.getMapList(placeQuery);
		List<DomainInfo> PlacesList = new ArrayList<DomainInfo>();

		for (int i = 0; i < list1.size(); i++) {

			HashMap param = (HashMap) list1.get(i);

			String iddomainInfo = StringHelperNew.n2s(param
					.get("iddomainInfo"));
			String domainId = StringHelperNew
					.n2s(param.get("domainId"));
			String description = StringHelperNew.n2s(param
					.get("description"));
			String location = StringHelperNew
					.n2s(param.get("location"));
			String updatedDate = StringHelperNew.n2s(param
					.get("updatedDate"));
			String photourl = StringHelperNew
					.n2s(param.get("photourl"));
			String pincode = StringHelperNew.n2s(param.get("pincode"));
			String lati = StringHelperNew.n2s(param.get("latitude"));
			String longi = StringHelperNew.n2s(param.get("longitude"));
			String rating = StringHelperNew.n2s(param.get("rating"));
			String ratinguserName = StringHelperNew.n2s(param
					.get("ratinguserName"));
			String phoneNumber = StringHelperNew.n2s(param
					.get("phoneNumber"));
			String review = StringHelperNew.n2s(param.get("review"));

			DomainInfo domainInfo = new DomainInfo(iddomainInfo,
					domainId, description, location, updatedDate,
					photourl, pincode, lati, longi, rating,
					ratinguserName, phoneNumber, review);
			PlacesList.add(domainInfo);

		}

		System.out.println("In  Ajax getPlaces phone PlacesList"
				+ PlacesList.size());
		for (DomainInfo domainInfo : PlacesList) {

			PlaceEntity placeEntity = new PlaceEntity();
			PlaceDetailEntity placeDetailEntity = new PlaceDetailEntity();

			placeEntity.setLatitude(StringHelperNew.n2d(domainInfo
					.getLatitude()));
			placeEntity.setLongitude(StringHelperNew.n2d(domainInfo
					.getLongitude()));
			ReviewEntity[] reviewEntityArr = new ReviewEntity[1];
			ReviewEntity reviewEntity = new ReviewEntity();

			placeDetailEntity.setName(domainInfo.getDescription());
			placeDetailEntity.setFormatted_address(domainInfo
					.getLocation());
			placeDetailEntity.setFormatted_phone_number(domainInfo
					.getPhoneNumber());

			reviewEntity.setAuthorName(domainInfo.getRatinguserName());
			reviewEntity.setText(domainInfo.getReview());
			reviewEntityArr[0] = reviewEntity;
			placeDetailEntity.setReviews(reviewEntityArr);

			placeDetailEntity.setUser_ratings(StringHelperNew
					.n2i(domainInfo.getRating()));
			placeEntity.setServer(true);
			placeEntity.setDetailEntity(placeDetailEntity);
			placeEntities.add(placeEntity);

		}
		for (int i = 0; i < placeEntities.size(); i++) {
			PlaceEntity placeEntity = (PlaceEntity) placeEntities
					.get(i);
			try {
				double inputDistance = Double.parseDouble(distance);
				double placeDistance = ConnectionManager.distance(
						placeEntity.getLatitude(),
						placeEntity.getLongitude(),
						Double.parseDouble(latitude),
						Double.parseDouble(longitude));

				if (placeDistance <= inputDistance / 1000) {
					System.out.println("Place NAme =>[" + placeDistance
							+ "] Filtered "
							+ placeEntity.getDetailEntity().getName());
					placeEntity.setServer(true);
					placeEntitiesFilterred.add(placeEntity);
				} else {
					System.out.println("Place NAme =>[" + placeDistance
							+ "] "
							+ placeEntity.getDetailEntity().getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("All Attractions of Mentioned Type => "
				+ placeEntities.size());

		System.out.println("All Attractions Within[" + distance
				+ "] of Mentioned Type=>"
				+ placeEntitiesFilterred.size());

		ObjectOutputStream os = new ObjectOutputStream(responseBody);
		os.writeObject(placeEntitiesFilterred);
		os.flush();
		os.close();

	}
%>


