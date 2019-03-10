<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
			<center>
			<h3>Add Contact Details</h3>
			</center>
			<br />
			<div id="col1" style="text-align: center;">
				<form id="commentForm" method="post" name="myform">
					<fieldset>
						<table style="width: 90%; text-align: left;">
							<tr style="font-family: verdana; font-size: 19">
								<td>Search According To Username&nbsp;&nbsp;<em>:</em>&nbsp;&nbsp;<input
									type="text" name="uname" class="required" minlength="1"></td>
								<td><input type="button" onclick="fnFetchData();"
									value="Search User"></input></td>
							</tr>

						</table>
						<div style="Display: none;" id="contentIdDiv">

							<p>
								User ID&nbsp;&nbsp;:&nbsp;&nbsp;<span id="userid"></span>
							</p>
							<p>
								User Name&nbsp;&nbsp;:&nbsp;&nbsp;<span id="uname"></span>
							</p>
							<p>
								IMEI&nbsp;&nbsp;:&nbsp;&nbsp;<span id="imei"></span>
							</p>
							<input type="text" name="imei" id="imei"></input>
							<fieldset>
								<legend>Enter Contact Details</legend>
								<p>Note:</p>
								<p>-Enter Only Numbers.</p>
								<p>-One Number is Compulsary.</p>

								<br>
									<p>
										Number&nbsp;&nbsp;:&nbsp;&nbsp;<input type="text" name="phn"
											minlength="10" id="num"></input>
									</p>
							</fieldset>
							<p>
								&nbsp;&nbsp;:&nbsp;&nbsp;<span id="imei"></span>
							</p>
							<input type="button" onclick="fnAddDetails()" name="verify"
								value="Add">
						</div>
					</fieldset>
				</form>
			</div>
			<div id="col2">
				<img src="<%=request.getContextPath()%>/theme/images/url.jpg" alt=""
					width="320" height="260" class="image-style" />
			</div>
		</div>

		<!-- end #page -->
	</div>

	<script>
 

  
  
  
  function fnFetchData() {
	  $('#commentForm').valid();
	 if ($('#commentForm').valid()) // check if form is valid
     {
		 dataString = $("#commentForm").serialize();
			$.ajax({
				  type: "POST",
				  url: "<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=searchName",
				  dataType: "json",
				  data:dataString
				}).done(function( msg ) {
					
					
					$("#contentIdDiv").fadeIn("3000");  
					$("#userid").html("&nbsp;"+msg.userid);
					$("#uname").html("&nbsp;"+msg.displayName);
					$("#imei").html("&nbsp;"+msg.imei);
				
			});

     }
     else 
     {
    	 alert("Fill all Fields");
     }
  }
  
  
  

  
  function  fnAddDetails() {
	  $('#commentForm').valid();
	 if ($('#commentForm').valid()) // check if form is valid
     {
		 dataString = $("#commentForm").serialize();
			$.ajax({
				  type: "POST",
				  url: "<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=searchStudent",
				  dataType: "json",
				  data:dataString
				}).done(function( msg ) {
					
					$("#contentIdDiv").fadeIn("3000");  
					$("#userid").html("&nbsp;"+msg.fname+" "+msg.lname);
					$("#uname").html("&nbsp;"+msg.fname+" "+msg.lname);
					$("#imei").html("&nbsp;"+msg.pfname+" "+msg.plname);
				
			});

     }
     else 
     {
    	 alert("Fill all Fields");
     }
  }
  
  $(document).ready(function(){
	  $("#domain").load("<%=request.getContextPath()%>
		/pages/tiles/ajax.jsp?method=getDomain");

						});
	</script>

</body>
</html>
