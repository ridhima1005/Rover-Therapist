<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.helper.UserAccount"%>
<%@page import="java.util.Date"%>
<%@page import="com.constants.ServerConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../theme/include.jsp" />

</head>
<body>
	<div id="wrapper">
		<%@include file="../theme/header.jsp"%>
		<!-- end #header -->
		<%@include file="../theme/menu.jsp"%>

		<div id="welcome">
			<h2 class="title">
				<a href="#">Welcome to <%=ServerConstants.APP_TITLE%></a>
			</h2>
			<div class="entry"></div>
		</div>
		<div id="two-columns">
			<b> <%
 	UserAccount ua = (UserAccount) session.getAttribute("USER_MODEL");
 	String name = ua.getUsername();
 %> Welcome&nbsp;<%=name%> <br />

				<div id="col2">
					<img src="<%=request.getContextPath()%>/theme/images/location.jpg"
						alt="" width="320" height="260" class="image-style" />
				</div>
		</div>

		<!-- end #page -->
	</div>



</body>
</html>
