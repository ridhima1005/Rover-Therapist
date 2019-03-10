<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="<%=request.getContextPath()%>/jquery.autocomplete-1.1.3/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jquery_validation/jquery.validate.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form class="cmxform" id="commentForm" method="get" action="">
		<fieldset>
			<legend>A simple comment form with submit validation and
				default messages</legend>
			<p>
				<label for="cname">Name</label> <em>*</em><input id="cname"
					name="name" size="25" class="required" minlength="2" />
			</p>
			<p>
				<input class="submit" type="submit" value="Submit" />
			</p>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			$("#commentForm").validate();
		});
	</script>
</body>
</html>