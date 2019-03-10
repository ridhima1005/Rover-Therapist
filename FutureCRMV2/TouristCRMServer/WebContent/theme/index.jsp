<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Employee Portal</title>
<script
	src="<%=request.getContextPath()%>/jquery.autocomplete-1.1.3/jquery-1.7.2.js"></script>
<link href="http://fonts.googleapis.com/css?family=Oswald"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/theme/style.css"
	rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
	<div id="wrapper">
		<jsp:include page="./header.jsp" />

		<!-- end #header -->

		<jsp:include page="./menu.jsp" />

		<div id="welcome">
			<h2 class="title">
			
			</h2>
			<div class="entry">
				<p>
					This is <strong>EmployeePortal</strong> where you can register for
					Remote Desktop App on Android.
				</p>
			</div>
		</div>

		<!-- end #page -->
	</div>

	<!-- end #footer -->

</body>
</html>
