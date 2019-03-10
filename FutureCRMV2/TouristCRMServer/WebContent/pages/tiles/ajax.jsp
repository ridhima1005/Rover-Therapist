
<%@page import="com.entities.PlaceEntity"%>
<%@page import="com.helper.UserAccount"%>
<%@page import="com.helper.DomainInfo"%>
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
<%@page import="com.helper.DBUtils"%>
<%@page import="com.helper.StringHelperNew"%>
<%@page import="org.json.JSONTokener"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.*"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
	//login
	String method = StringHelperNew.n2s(request.getParameter("method"));
	String id = StringHelperNew.n2s(request.getParameter("id"));
	System.out.println(method + " " + id);
	java.util.HashMap parameters = com.helper.StringHelperNew
			.displayRequest(request);
	String str = "";

	if (method.equalsIgnoreCase("getDomain")) {
		str = ConnectionManager.getDomain();
		response.getWriter().write(str);
		response.getWriter().close();
	} else if (method.equalsIgnoreCase("logout")) {
		session.removeAttribute("USER_MODEL");
%>
<script>
	window.location.href='<%=request.getContextPath()%>/pages/index.jsp';
</script>
<%
	//Search Student using name
	}
	//update Password
	else if (method.equalsIgnoreCase("updatePhn")) {
		String phn = StringHelperNew.n2s(request.getParameter("phn"));
		String imei = StringHelperNew.n2s(request.getParameter("imei"));
		PrintWriter pw = response.getWriter();
		int i = ConnectionManager.updatePhn(imei, phn);
		if (i > 0) {
			pw.println("Phone No updated successfully");
		} else {
			pw.println("Error Saving Details");
		}
		pw.close();
	}

	else if (method.equalsIgnoreCase("login")) {
		boolean success = ConnectionManager.checkLogin(parameters,
				session);
		if (success) {
%>
<script>
		alert("Login Successfull!");
		window.location.href='<
	%=request.getContext
Path()%>/pages/home.jsp';
	</script>
<%
	} else {
%>
<script>
		alert("Login Failed! Incorrect Login ID or Password
	!");
		window.location.href=
	'<%=request.getContextPath()%>/pages/index.jsp';
	</script>
<%
	}
	}
	//add details
	else if (method.equalsIgnoreCase("addDetails")) {

		PrintWriter pw = response.getWriter();
		String domain = StringHelperNew.n2s(request
				.getParameter("domain"));
		String locationDetails = StringHelperNew.n2s(request
				.getParameter("locationDetails"));
		String locationde = StringHelperNew.n2s(request
				.getParameter("locationde"));
		String locationPh = StringHelperNew.n2s(request
				.getParameter("locationPh"));
		String reviewer = StringHelperNew.n2s(request
				.getParameter("reviewer"));
		String rating = StringHelperNew.n2s(request
				.getParameter("rating"));
		String latitude = StringHelperNew.n2s(request
				.getParameter("latitude"));
		String longitude = StringHelperNew.n2s(request
				.getParameter("longitude"));

		int i = ConnectionManager.addDetails(domain, locationde,
				locationDetails, locationPh, reviewer, rating,
				latitude, longitude);
		if (i == 1) {
			pw.println("Tourist Area  Added Successfully");
		} else {
			pw.println(" Updation Failed");
		}

	}

	//searching
	else if (method.equalsIgnoreCase("fetchDetails")) {
		String domain = StringHelperNew.n2s(request
				.getParameter("domain"));
		String query = "SELECT * FROM `domaininfo` where domainid='"
				+ domain + "'";
		System.out.println("Query " + query);
		List list = DBUtils.getBeanList(DomainInfo.class, query);
		System.out.println("List Category " + list.size());
		session.setAttribute("RESULT", list);
		request.getRequestDispatcher("/pages/tiles/domaintile.jsp")
				.forward(request, response);

	}

	else if (method.equalsIgnoreCase("insertUserDetailsObject")) {
		ConnectionManager.insertUser(parameters);

	}
	//delete
	else if (method.equalsIgnoreCase("deleteAdds")) {
		PrintWriter pw = response.getWriter();
		String imei = StringHelperNew.n2s(request.getParameter("imei"));
		int i = ConnectionManager.deleteAdds(imei);

	}

	else if (method.equalsIgnoreCase("deleteUser")) {
		PrintWriter pw = response.getWriter();
		String domainid = StringHelperNew.n2s(request
				.getParameter("id"));

		int i = ConnectionManager.deleteUser(domainid);

	} else if (method.equalsIgnoreCase("userDetails")) {
		String imei = StringHelperNew.n2s(request.getParameter("imei"));
		int success = ConnectionManager.getUserId(imei);
		OutputStream os = response.getOutputStream();
		os.write((success + "").getBytes());
		os.flush();
		os.close();
		return;
	} else if (method.equalsIgnoreCase("getPlaces")) {
		System.out.println("In  Ajax getPlaces ");
		List placesList = ConnectionManager.getPlace(parameters);
		ObjectOutputStream os = new ObjectOutputStream(
				response.getOutputStream());
		os.writeObject(placesList);
		os.flush();
		os.close();
	}
%>


