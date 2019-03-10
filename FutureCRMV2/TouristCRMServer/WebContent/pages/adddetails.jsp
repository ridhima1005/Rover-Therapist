<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Date"%>
<%@page import="com.constants.ServerConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="../theme/include.jsp" />
 
</head>  
<body>  
<div id="wrapper">  
<%@include file="../theme/header.jsp" %>  
	<!-- end #header -->  
<%@include file="../theme/menu.jsp" %>  

	<div id="welcome">
		<h2 class="title"><a href="#">Welcome to <%=ServerConstants.APP_TITLE %></a></h2>
		<div class="entry">
		
			
			
		</div>
	</div>
	<div id="two-columns">
		<center><h3>Add Tourist Area Details</h3></center>
		<br/>
		<div id="col1" style="text-align: center;">
		<form 
		id="commentForm" method="post" name="myform" >
		<fieldset>		
		<table style="width: 90%;text-align: left;">
		    <tr style="font-family: verdana;font-size: 19">
			<td>Area Type &nbsp<em>*</em></td>
				<td><select validate="required" title="Please Select Type" id="domain" name="domain" >
			
				</select></td>
			</tr>
		<tr style="font-family: verdana;font-size: 19">
				<td>Location&nbsp; Name<em>*</em></td>
				<td>
				<input type="text"  name="locationDetails"  id="locationDetails" />
				</td>
			</tr>
			<tr style="font-family: verdana;font-size: 19">
				<td>Location&nbsp; Description<em>*</em></td>
				<td>
				<textarea rows="3" cols="50" name="locationde" ></textarea>
				</td>
			</tr>
			
			<tr style="font-family: verdana;font-size: 19">
				<td>Phone &nbsp; Number<em></em></td>
				<td>
				<input type="text"  name="locationPh"  id="locationPh" />
				</td>
			</tr>
			
			<tr style="font-family: verdana;font-size: 19">
				<td>Owner  &nbsp; Name<em></em></td>
				<td>
				<input type="text"  name="reviewer"  id="reviewer" />
				</td>
			</tr>
			
			
			
			    <tr style="font-family: verdana;font-size: 19">
			<td>Rating &nbsp<em></em></td>
				<td><select validate="required" title="Rating" id="rating" name="rating" >
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
			<option>6</option>
			<option>7</option>
			<option>8</option>
			<option>9</option>
			<option>10</option>
				</select></td>
			
			<tr>
				<td colspan=2  style="text-align: center;"><input type="button" onClick="fnAddDetails()" name="verify"  value="Add" ></td>
			</tr>
		</table>
		
 		</fieldset> 
	</form>
		</div>
		<div id="col2"><img src="<%=request.getContextPath()%>/theme/images/map_location.jpg" alt="" width="200" height="200" class="image-style" /> </div>
		Location Details &nbsp<em>*</em>
			<iframe src="define_map_poly.jsp?areatype=Add Tourist Location" style="width: 120%;height: 300px;;border: none;border-style: none;overflow: hidden; left;"  name="disasterId" id="disasterId" ></iframe>
	</div>
	
	
	<!-- end #page --> 
</div>

  <script>
  /* $(document).ready(function(){
    $("#commentForm").validate();
  });
  */
 
  
  function fnAddDetails() {
	 // $('#commentForm').valid();
	 if ($('#commentForm').valid()) // check if form is valid
     {
		 
		 document.getElementById('disasterId').contentWindow.fnShowPath();
		   alert(document.getElementById('disasterId').contentWindow.$('#dataPanel').html());
		   
		   var latLong=document.getElementById('disasterId').contentWindow.$('#dataPanel').html();
		  	var latLongArr= latLong.split(",");
		  	var lat=latLongArr[0];
		  	var longi=latLongArr[1];
		lat=lat.substring(2);
		longi=longi.substring(0,(longi.indexOf(")")-1));
			/* document.getElementById('latlong').value=document.getElementById('disasterId').contentWindow.$('#dataPanel').html(); */
			
		 
		  dataString = $("#commentForm").serialize();
		  dataString  = dataString+"&latitude="+lat+"&longitude="+longi;
			alert(dataString);
			
		
			$.ajax({
				  type: "POST",
				  url: "<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=addDetails",
				  dataType: "text",
				  data:dataString
				  }).done(function(msg){
					alert(msg.trim());
					$('#commentForm')[0].reset();
			}); 
     }
     else 
     {
    	 alert("Fill all Fields");
     }
  }
  $(document).ready(function(){
	  $("#domain").load("<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=getDomain"); /*getDomain from ConnectionManager class of com.util package is called*/
	  
});

   
  </script>

</body>
</html>
