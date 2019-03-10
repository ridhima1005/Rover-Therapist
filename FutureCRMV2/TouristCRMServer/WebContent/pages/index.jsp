<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
			<div id="col1" style="text-align: center;">
				<form
					action="<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=login"
					class="cmxform" id="commentForm" method="post" name="myform">

					<fieldset>
						<table style="width: 80%; text-align: left;">

							<tr>
								<td>Username<em>*</em></td>
								<td><input type="text" name="userId" class="required"
									size="25" minlength="2"></td>
							</tr>
							<tr>
								<td>Password<em>*</em></td>
								<td><input type="password" name="pass" class="required"
									size="25" minlength="2"></td>
							</tr>

							<tr>
								<td></td>
								<td colspan="2" style="text-align: left;"><input
									class="submit" type="submit" name="verifiy" value="Login"></td>
							</tr>

						</table>
					</fieldset>
				</form>
			</div>
			<div id="col2">
				<img
					src="<%=request.getContextPath()%>/theme/images/map_location.jpg"
					alt="" width="320" height="260" class="image-style" />
			</div>
		</div>
		<!-- end #page -->
	</div>
	<script>
		$(document).ready(function() {
			$("#commentForm").validate();
		});
	</script>


</body>
</html>
