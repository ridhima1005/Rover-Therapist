<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="com.helper.StringHelperNew"%>
<%@page import="com.helper.UserAccount"%>
<%@page import="java.util.List"%>
<%@page import="com.util.WebConnectionManager"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>Location Based Add</title>
<script
	src="<%=request.getContextPath()%>/jquery.autocomplete-1.1.3/jquery-1.7.2.js"></script>
<link href="http://fonts.googleapis.com/css?family=Oswald"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/theme/style.css"
	rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
	<div id="wrapper">
		<jsp:include page="../theme/header.jsp" />

		<!-- end #header -->

		<jsp:include page="../theme/menu.jsp" />

		<div id="welcome">
			<h2 class="title">
				
			</h2>
			<div class="entry">
				<%
					List list = WebConnectionManager.getAllUsers();
					session.setAttribute("RESULT", list);
				%>
				<%@page import="com.helper.DomainInfo"%>
				<%@ taglib uri="/WEB-INF/displaytag-12.tld" prefix="display"%>
				<display:table class="simple" name="sessionScope.RESULT"
					requestURI="/pages/deleteAdds.jsp" id="searchTableId" pagesize="20"
					style=" width:100%;" defaultsort="1" defaultorder="ascending"
					export="false" sort="list">
					<display:setProperty name="export.csv" value="false" />
					<display:setProperty name="export.xml" value="false" />
					<display:setProperty name="export.xls" value="false" />
					<display:setProperty name="paging.banner.placement" value="bottom" />


					<display:column title="Display Name" sortable="true"
						property="displayName">
					</display:column>
					<display:column title="Email" sortable="true" property="mailid">
					</display:column>
					<display:column title="Phone No" sortable="true" property="phoneno">
					</display:column>
					<display:column title="IMEI" sortable="true" property="imei">
					</display:column>
					
					<display:column sortable="true" title="Delete " media="html">
						<a href="#"
							onclick="fnDeleteDetails('<%=((UserAccount) (pageContext
							.getAttribute("searchTableId"))).getImei()%>')">Delete
							User</a>
					</display:column>
				</display:table>

				<div id="divId" style="display: none;">
					<p>
						User Display Name&nbsp;&nbsp;:&nbsp;&nbsp;<input type="text"
							name="disp" readonly="readonly" id="disp"></input>
					</p>
					<p>
						User Id&nbsp;&nbsp;:&nbsp;&nbsp;<input type="text" name="imei"
							readonly="readonly" id="imei"></input>
					</p>
					<p>
						Enter Father's Contact Number&nbsp;&nbsp;:&nbsp;&nbsp;<input
							type="text" name="phn" minlength="10" id="phn"></input>
					</p>
					<p>
						Existing Contacts <span id="ExistingId"></span>
					</p>
					<p>
						<input type="button" value="Add Contact" onclick="fnAdd()" /><input
							type="button" value="Remove All Contacts" />
					</p>

				</div>
				x

			</div>
		</div>

		<!-- end #page -->
	</div>

	<!-- end #footer -->
	<script>
function fnUpdateDetails(imei,fc,disp){
	$("#divId").show();
	$("#imei").val(imei);
	$("#ExistingId").html(fc);
	$("#phn").val('');   
	$("#disp").val(disp);
}
function fnDeleteDetails(imei){
	if(confirm("Are you sure ?")){
		$.ajax({
			  type: "POST",
			  url: "<%=request.getContextPath()%>/pages/tiles/ajax.jsp?method=deleteUser&id="+imei,
			  dataType: "text",
			  data:""
		}).done(function( msg ) {  
			alert(" User Deleted");
			window.location.reload();
		});	
		}
}

function fnAdd(){
	fc=$("#ExistingId").html();
	phn='';
	im=$("#imei").val();
	if(fc.length>0){
		phn=fc+","+$("#phn").val();
	}else{
		phn=$("#phn").val();
	}
	dataString='phn='+phn+"&imei="+im;
	alert(dataString);
	$.ajax({
		  type: "POST",
		  url: "<%=request.getContextPath()%>
		/pages/tiles/ajax.jsp?method=updatePhn",
								dataType : "text",
								data : dataString
							}).done(function(msg) {
						alert(msg);
					});
		}
	</script>
</body>
</html>
